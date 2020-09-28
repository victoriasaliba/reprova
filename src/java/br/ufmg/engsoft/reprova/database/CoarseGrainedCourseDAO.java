package br.ufmg.engsoft.reprova.database;

import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.CoarseGrainedCourse;
import br.ufmg.engsoft.reprova.model.Course;


public class CoarseGrainedCourseDAO extends CourseDAO {
    /*TODO: TEST THIS CLASS*/

    /**
     * Basic constructor.
     *
     * @param db   the database, mustn't be null
     * @param json the json formatter for the database's documents, mustn't be null
     * @throws IllegalArgumentException if any parameter is null
     */
    public CoarseGrainedCourseDAO(Mongo db, Json json) {
        super(db, json);
    }

    @Override
    public void add(Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException("course mustn't be null");
    	}
    	
    	Document doc = new Document()
    			.append("year", course.year)
    			.append("ref", course.ref.value)
    			.append("courseName", course.courseName)
    			.append("scores", ((CoarseGrainedCourse) course).score);
    	this.collection.insertOne(doc);
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
        return new CoarseGrainedCourse(doc.getInteger("year"),
        							   Course.Reference.fromInt(doc.getInteger("ref")),
        							   doc.getString("courseName"),
        							   doc.getDouble("scores").floatValue());
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
