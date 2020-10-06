package br.ufmg.engsoft.reprova.model;

public abstract class ScoreFileFactory {
    public static ScoreFileFactory create() {
        return null;
    }
    public abstract ScoreFile createScoreFile(String submittedFileName);
}
