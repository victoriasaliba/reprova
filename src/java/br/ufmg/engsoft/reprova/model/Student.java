package br.ufmg.engsoft.reprova.model;

import java.util.List;
import java.util.Objects;

public class Student {
    public final String id;

    public final float score;

    public Student(String id, float score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Float.compare(student.score, score) == 0 &&
                Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score);
    }
    
    public float calculateScore(List<Student> students) {
		float totalScore = 0;
		for (Student student : students) {
			totalScore += student.score;
		}
		return totalScore;
    }
}
