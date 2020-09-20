package br.ufmg.engsoft.reprova.model;

import br.ufmg.engsoft.reprova.model.variability.CoarseGrainedCourseFactory;

import java.util.List;

public abstract class CourseFactory {
    public static CourseFactory create() {
        return new CoarseGrainedCourseFactory();  // TODO: apply variance
    }
    public abstract Course createCourse(int year, Course.Reference ref, String courseName, float score);
    public abstract Course createCourse(int year, Course.Reference ref, String courseName, List<Student> students);
}