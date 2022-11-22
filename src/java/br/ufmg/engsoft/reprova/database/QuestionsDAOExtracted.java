package br.ufmg.engsoft.reprova.database;

import java.util.*;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import br.ufmg.engsoft.reprova.model.Question;
import org.bson.types.ObjectId;
import java.util.Collection;
import java.util.List;
import org.bson.conversions.Bson;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import com.mongodb.client.FindIterable;
import java.util.ArrayList;
import br.ufmg.engsoft.reprova.model.Course;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.fields;

import br.ufmg.engsoft.reprova.mime.json.Json;	
import br.ufmg.engsoft.reprova.model.Question;

		
import org.slf4j.Logger;	
import org.slf4j.LoggerFactory;

public class QuestionsDAOExtracted {
	private final MongoCollection<Document> collection;

	public QuestionsDAOExtracted(MongoCollection<Document> getCollection) {
		this.collection = getCollection;
	}

	/**
	* Get the question with the given id.
	* @param id   the question's id in the database.
	* @return  The question, or null if no such question.
	* @throws IllegalArgumentException   if any parameter is null
	*/
	public Question get(String id) {
		if (id == null)
			throw new IllegalArgumentException("id mustn't be null");
		Question question = this.collection.find(eq(new ObjectId(id))).map(this::parseDoc).first();
		if (question == null)
			QuestionsDAO.getLogger().info("No such question " + id);
		return question;
	}

	/**
	* List all the questions that match the given non-null parameters. The question's statement is commited.
	* @param theme       the expected theme, or null
	* @param pvt         the expected privacy, or null
	* @return  The questions in the collection that match the given parameters, possibly empty.
	* @throws IllegalArgumentException   if there is an invalid Question
	*/
	public Collection<Question> list(String theme, Boolean pvt) {
		List<Bson> filters = Arrays
				.asList(theme == null ? null : eq("theme", theme), pvt == null ? null : eq("pvt", pvt)).stream()
				.filter(Objects::nonNull).collect(Collectors.toList());
		FindIterable<Document> doc = filters.isEmpty() ? this.collection.find() : this.collection.find(and(filters));
		ArrayList<Question> result = new ArrayList<Question>();
		doc.projection(fields(exclude("statement"))).map(this::parseDoc).into(result);
		return result;
	}

	/**
	* Remove the question with the given id from the collection.
	* @param id   the question id
	* @return  Whether the given question was removed.
	* @throws IllegalArgumentException   if any parameter is null
	*/
	public boolean remove(String id) {
		if (id == null)
			throw new IllegalArgumentException("id mustn't be null");
		boolean result = this.collection.deleteOne(eq(new ObjectId(id))).wasAcknowledged();
		if (result)
			QuestionsDAO.getLogger().info("Deleted question " + id);
		else
			QuestionsDAO.getLogger().warn("Failed to delete question " + id);
		return result;
	}

	/**
	* Adds or updates the given question in the database. If the given question has an id, update, otherwise add.
	* @param question   the question to be stored
	* @return  Whether the question was successfully added.
	* @throws IllegalArgumentException   if any parameter is null
	*/
	public boolean add(Question question) {
		if (question == null)
			throw new IllegalArgumentException("question mustn't be null");

		Document doc = question.createDocument();
			String id = question.id;

		if (id != null) {
			UpdateResult result = this.collection.replaceOne(eq(new ObjectId(id)), doc);
			if (!result.wasAcknowledged()) {
				QuestionsDAO.getLogger().warn("Failed to replace question " + id);
				return false;
			}
		} else
			this.collection.insertOne(doc);
		
		return true;
	}

	public static Logger getLogger() {
		return logger;
	}
}