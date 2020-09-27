package br.ufmg.engsoft.reprova.model.variability;

import br.ufmg.engsoft.reprova.model.CoarseGrainedCourse;
import br.ufmg.engsoft.reprova.model.Course;
import br.ufmg.engsoft.reprova.model.CourseFactory;
import br.ufmg.engsoft.reprova.model.Student;

import java.util.List;

public class CoarseGrainedCourseFactory extends CourseFactory{

    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName) {
        return new CoarseGrainedCourse(year,ref,courseName,0.0f);
    }

    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName, float score) {
        return new CoarseGrainedCourse(year,ref,courseName,score);
    }

    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName, List<Student> students) {
        throw new IllegalArgumentException("The system is configured to store course score aggregately!");
    }
}
