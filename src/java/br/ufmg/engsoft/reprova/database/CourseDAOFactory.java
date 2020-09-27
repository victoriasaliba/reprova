package br.ufmg.engsoft.reprova.database;

import br.ufmg.engsoft.reprova.mime.json.Json;

public abstract class CourseDAOFactory {
    public static CourseDAOFactory create() {
        return null;
    }
    public abstract CourseDAO createCourseDAO(Mongo db, Json json);
}
