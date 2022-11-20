package br.ufmg.engsoft.reprova.model;

import java.util.List;

public class FineGrainedCourse extends Course {
    public final List<Student> students;

    public FineGrainedCourse(int year, Reference ref, String courseName, List<Student> students) {
        super(year, ref, courseName);
        this.students = students;
    }

    @Override
    public float getScore() {
        float totalScore = calculateScore();
		return totalScore / this.students.size();
    }

	private float calculateScore() {
		float totalScore = 0;
		for (Student student : students) {
			totalScore += student.score;
		}
		return totalScore;
	}
}

