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
        Float scoreSum = 0.0f;
        for(Student student: students){
            scoreSum += student.score;
        }
        Float averageScore = scoreSum/students.size();
        return new CoarseGrainedCourse(year,ref,courseName,averageScore);
    }

}
