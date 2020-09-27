package br.ufmg.engsoft.reprova.model;

import java.util.List;

public abstract class CourseFactory {
    public static CourseFactory create() {
        return null;
    }
    public abstract Course createCourse(int year,Course.Reference ref, String courseName);
    public abstract Course createCourse(int year, Course.Reference ref, String courseName, float score);
    public abstract Course createCourse(int year, Course.Reference ref, String courseName, List<Student> students);
}