package br.ufmg.engsoft.reprova.model;

public class CoarseGrainedCourse extends Course {
    /**
     * TODO: SONIA CUIDE DISSO!
     */
    public final float score;

    public CoarseGrainedCourse(int year, Reference ref, String courseName, float score) {
        super(year, ref, courseName);
        this.score = score;
    }

    @Override
    public float getScore() {
        return this.score;
    }
}
