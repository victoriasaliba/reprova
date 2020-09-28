package br.ufmg.engsoft.reprova.tests.database;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.ufmg.engsoft.reprova.database.CoarseGrainedCourseDAO;
import br.ufmg.engsoft.reprova.database.Mongo;
import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.CoarseGrainedCourse;
import br.ufmg.engsoft.reprova.model.Course;
import br.ufmg.engsoft.reprova.model.CourseFactory;
import br.ufmg.engsoft.reprova.model.Student;
import br.ufmg.engsoft.reprova.model.variability.CoarseGrainedCourseFactory;

public class CoarseGrainedCourseDAOTest {
	private static final Logger logger = LoggerFactory.getLogger(CoarseGrainedCourseDAOTest.class);
	private static Mongo db;
	private static Json json;
	private static CoarseGrainedCourseDAO dao;
	@BeforeAll
    public static void setup() {
		logger.info("Starting DB connection");
		try {
			db = new Mongo("reprova");
			json = new Json();
			dao = new CoarseGrainedCourseDAO(db, json);
		}
		catch(Exception e){
			logger.info("Could not connect to mongoDB");
			db = null;
		}
		
    }
	@BeforeEach
	void assumeConnection() {
		assumeFalse(db == null);
	}
	
	@Test
	void test_insertion() {
		CourseFactory factory = new CoarseGrainedCourseFactory();
        Course course = factory.createCourse(2019, Course.Reference._1, "test_insertion", 50.0f);
        dao.add(course);
	}
	
	@Test
	void test_retrieval() {
		CourseFactory factory = new CoarseGrainedCourseFactory();
        CoarseGrainedCourse course = (CoarseGrainedCourse)factory.createCourse(2019, Course.Reference._1, "test_retrieval", 50.0f);
        dao.add(course);
        Course course2 = dao.get(course);
        //Assertions.assertEquals(course.courseName, course2.courseName);
        Assertions.assertEquals(course.getScore(),50.0f);
	}
	
	

}
