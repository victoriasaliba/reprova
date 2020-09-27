package br.ufmg.engsoft.reprova.database;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Course;

public class CoarseGrainedCourseDAO extends CourseDAO {
    /*TODO: IMPLEMENT THIS CLASS*/

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

    }

    @Override
    public Course get(Course course) {
        return null;
    }

    @Override
    public void delete(Course course) {

    }
}
