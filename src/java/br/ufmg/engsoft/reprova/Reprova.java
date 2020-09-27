package br.ufmg.engsoft.reprova;

import br.ufmg.engsoft.reprova.database.CourseDAO;
import br.ufmg.engsoft.reprova.database.CourseDAOFactory;
import br.ufmg.engsoft.reprova.database.Mongo;
import br.ufmg.engsoft.reprova.database.QuestionsDAO;
import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.routes.Setup;


public class Reprova {
  public static void main(String[] args) {
    Json json = new Json();

    Mongo db = new Mongo("reprova");

    QuestionsDAO questionsDAO = new QuestionsDAO(db, json);
    CourseDAO courseDAO = CourseDAOFactory.create().createCourseDAO(db,json);

    Setup.routes(json, questionsDAO,courseDAO);
  }
}
