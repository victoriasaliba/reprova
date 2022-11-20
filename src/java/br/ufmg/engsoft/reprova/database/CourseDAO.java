package br.ufmg.engsoft.reprova.database;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Course;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CourseDAO {
    /**
     * Logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(QuestionsDAO.class);

    /**
     * Json formatter.
     */
    protected final Json json;

    /**
     * Questions collection.
     */
    protected final MongoCollection<Document> collection;
    /**
     * Basic constructor.
     * @param db    the database, mustn't be null
     * @param json  the json formatter for the database's documents, mustn't be null
     * @throws IllegalArgumentException  if any parameter is null
     */
    public CourseDAO(Mongo db, Json json) {
        if (db == null)
            throw new IllegalArgumentException("db mustn't be null");

        if (json == null)
            throw new IllegalArgumentException("json mustn't be null");

        this.collection = db.getCollection("questions");
        //this.collection.createIndex(Indexes.text("some_field3"), new IndexOptions().unique(true));
        this.collection.createIndex(Indexes.compoundIndex(
        													Indexes.ascending("year"),
        													Indexes.ascending("ref"),
        													Indexes.text("courseName")
        							), new IndexOptions().unique(true));

        this.json = json;
    }
    public abstract void add(Course course);
    public abstract Course get(Course course);
    public abstract boolean delete(Course course);
	public static Logger getLogger() {
		return logger;
	}
}
