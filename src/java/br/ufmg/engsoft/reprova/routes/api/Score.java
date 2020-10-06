package br.ufmg.engsoft.reprova.routes.api;

import br.ufmg.engsoft.reprova.database.CourseDAO;
import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Score {
    /**
     * Logger instance.
     */
    protected static final Logger logger = LoggerFactory.getLogger(Questions.class);
    /**
     * Messages.
     */
    protected static final String invalid = "\"Invalid request\"";
    protected static final String ok = "\"Ok\"";
    /**
     * Json formatter.
     */
    protected final Json json;

    protected final CourseDAO courseDAO;

    /**
     * Instantiate the questions endpoint.
     * The setup method must be called to install the endpoint.
     * @param json  the json formatter
     * @throws IllegalArgumentException  if any parameter is null
     */
    public Score(Json json, CourseDAO courseDAO) {
        if (json == null)
            throw new IllegalArgumentException("json mustn't be null");
        if (courseDAO == null)
            throw new IllegalArgumentException("courseDAO mustn't be null");

        this.json = json;
        this.courseDAO = courseDAO;
    }

    /**
     * Install the endpoint in Spark.
     * Methods:
     * - get
     * - post
     * - delete
     */
    public void setup() {
        Spark.get("/api/score", this::get);
        Spark.post("/api/score", this::post);
        Spark.delete("/api/score", this::delete);

        logger.info("Setup /api/score.");
    }
    /**
     * Post endpoint: add or update a course score in the database.
     * The score file must be supplied in the request's body.
     * If the course already exist in the database, the operation is an update.
     * Otherwise, the given course is added as a new one in the database.
     */
    protected Object post(Request request, Response response) {
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        List<Student> students = new ArrayList<Student>();
        Course course= null;

        try {
            Part part =request.raw().getPart("scores_file");
            ScoreFileFactory scoreFileFactory = ScoreFileFactory.create();
            ScoreFile scoreFile = scoreFileFactory.createScoreFile(part.getSubmittedFileName());
            InputStream inputStream = part.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Course scoredCourse = scoreFile.getScoredCourseFromFile(reader);
            reader.close();
            courseDAO.add(scoredCourse);

        }
        catch (Exception e){
            logger.error("Invalid request payload!", e);
            response.status(400);
            return invalid;
        }
        return ok;
    }
    /**
     * Get endpoint: fetch the specified scored course from the database.
     */
    protected Object get(Request request, Response response){
        Course course = getCourseFromRequest(request);
        course = courseDAO.get(course);
        return course;
    }

    private Course getCourseFromRequest(Request request) {
        String body = request.body();
        JsonParser jsonParser = new JsonParser();
        Object object = jsonParser.parse(body);
        JsonObject jsonBody = (JsonObject) object;
        Integer year = jsonBody.get("year").getAsInt();
        Integer referenceInteger = jsonBody.get("ref").getAsInt();
        Course.Reference reference = Course.Reference.fromInt(referenceInteger);
        String courseName = jsonBody.get("courseName").toString();
        Course course = CourseFactory.create().createCourse(year,reference,courseName);
        return course;
    }

    /**
     * Delete endpoint: remove a course from the database.
     * The question's info must be provided in the request
     */
    protected Object delete(Request request, Response response) {
        Course course = getCourseFromRequest(request);
        courseDAO.delete(course);
        return ok;
    }
}
