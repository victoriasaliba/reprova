package br.ufmg.engsoft.reprova.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreJson extends ScoreFile {
    private String format = ".json";
    @Override
    public Course getScoredCourseFromFile(BufferedReader reader) throws IOException {
        JsonParser jsonParser = new JsonParser();
        Object object = jsonParser.parse(reader);
        JsonObject jsonObject = (JsonObject) object;

        Course course = getCourse(jsonObject);
        List<Student> students = new ArrayList<Student>();

        JsonArray studentsList = (JsonArray) jsonObject.get("students");
        for(JsonElement studentJson : studentsList){
            Student student = getStudent(studentJson);
            students.add(student);
        }

        Course scoredCourse = CourseFactory.create().createCourse(course.year,course.ref,course.courseName,students);
        return scoredCourse;
    }

    private Student getStudent(JsonElement studentJson) {
        String id = studentJson.getAsJsonObject().get("id").toString();
        String scoreString = studentJson.getAsJsonObject().get("score").toString();
        Float score = Float.parseFloat(scoreString);
        Student student = new Student(id,score);
        return student;
    }

    private Course getCourse(JsonObject jsonObject) {
        JsonObject courseJson = (JsonObject) jsonObject.get("course");
        String courseName = courseJson.get("courseName").toString();
        String yearString = courseJson.get("year").toString();
        Integer year = Integer.parseInt(yearString);
        String referenceString = courseJson.get("ref").toString();
        Integer referenceInt = Integer.parseInt(referenceString);
        Course.Reference reference = Course.Reference.fromInt(referenceInt);
        CourseFactory courseFactory = CourseFactory.create();
        Course course = courseFactory.createCourse(year, reference, courseName);
        return course;
    }
}
