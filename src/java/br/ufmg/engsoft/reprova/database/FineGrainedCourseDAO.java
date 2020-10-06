package br.ufmg.engsoft.reprova.database;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Course;
import br.ufmg.engsoft.reprova.model.FineGrainedCourse;
import br.ufmg.engsoft.reprova.model.Student;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class FineGrainedCourseDAO extends CourseDAO {
    /**
     * Basic constructor.
     *
     * @param db   the database, mustn't be null
     * @param json the json formatter for the database's documents, mustn't be null
     * @throws IllegalArgumentException if any parameter is null
     */
    public FineGrainedCourseDAO(Mongo db, Json json) {
        super(db, json);
    }
    
    @Override
    public void add(Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException("course mustn't be null");
    	}
    	List<Student> students = ((FineGrainedCourse) course).students;
    	
    	Document doc = new Document()
    			.append("year", course.year)
    			.append("ref", course.ref.value)
    			.append("courseName", course.courseName)
    			.append("scores", students);
    	this.collection.replaceOne(and(
										eq("year", course.year),
										eq("ref", course.ref.value),
										eq("courseName", course.courseName)
    								), doc, (new UpdateOptions()).upsert(true));
    	logger.info("Stored course " + doc.get("courseName") +  ": " + doc.get("year") + "/" + doc.get("ref"));
    }

    @Override
    public Course get(Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException("course mustn't be null");
    	}
    	Document doc = this.collection.find(and(
    											eq("year", course.year),
    											eq("ref", course.ref.value),
    											eq("courseName", course.courseName)
    											)).first();
    	List<Student> students = (List<Student>) doc.get("scores");
        return new FineGrainedCourse(doc.getInteger("year"),
        							   Course.Reference.fromInt(doc.getInteger("ref")),
        							   doc.getString("courseName"),
        							   students);
    }

    @Override
    public boolean delete(Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException("course mustn't be null");
    	}
    	boolean result = this.collection.deleteOne(and(
														eq("year", course.year),
														eq("ref", course.ref.value),
														eq("courseName", course.courseName)
													)).wasAcknowledged();
    	if (result)
    		logger.info("Deleted course " + course.courseName +  ": " + course.year + "/" + course.ref.value);
    	else
    		logger.warn("Failed to delete course " + course.courseName +  ": " + course.year + "/" + course.ref.value);
    	return result;
    }
}
