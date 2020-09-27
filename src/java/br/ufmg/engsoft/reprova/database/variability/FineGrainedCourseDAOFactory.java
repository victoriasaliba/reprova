package br.ufmg.engsoft.reprova.database.variability;

import br.ufmg.engsoft.reprova.database.CourseDAO;
import br.ufmg.engsoft.reprova.database.CourseDAOFactory;
import br.ufmg.engsoft.reprova.database.FineGrainedCourseDAO;
import br.ufmg.engsoft.reprova.database.Mongo;
import br.ufmg.engsoft.reprova.mime.json.Json;

public class FineGrainedCourseDAOFactory extends CourseDAOFactory {
    @Override
    public CourseDAO createCourseDAO(Mongo db, Json json) {

        return new FineGrainedCourseDAO(db,json);
    }
}
