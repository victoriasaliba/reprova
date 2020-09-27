package br.ufmg.engsoft.reprova.model.variability;

import br.ufmg.engsoft.reprova.model.Course;
import br.ufmg.engsoft.reprova.model.CourseFactory;
import br.ufmg.engsoft.reprova.model.FineGrainedCourse;
import br.ufmg.engsoft.reprova.model.Student;

import java.util.ArrayList;
import java.util.List;

public class FineGrainedCourseFactory extends CourseFactory {
    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName) {
        List<Student> students = new ArrayList<Student>();
        return new FineGrainedCourse(year,ref,courseName,students);
    }

    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName, float score) {
        throw new IllegalArgumentException("The system is configured to store students' score individually!");
    }

    @Override
    public Course createCourse(int year, Course.Reference ref, String courseName, List<Student> students) {
        return new FineGrainedCourse(year,ref,courseName,students);
    }
}
