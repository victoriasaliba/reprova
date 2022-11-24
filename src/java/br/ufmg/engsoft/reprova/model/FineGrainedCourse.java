package br.ufmg.engsoft.reprova.model;

import java.util.List;

public class FineGrainedCourse extends Course {
    public final List<Student> students;
    public final Student student;

    public FineGrainedCourse(int year, Reference ref, String courseName, List<Student> students, Student student) {
        super(year, ref, courseName);
        this.students = students;
        this.student = student;
    }

    @Override
    public float getScore() {
        float totalScore = student.calculateScore(students);
		return totalScore / this.students.size();
    }
}

