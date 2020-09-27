package br.ufmg.engsoft.reprova.model;

public abstract class ScoreFile {
    private String format;
    public abstract Course courseFromLine(String scoreLine);
    public abstract Student studentFromLine(String scoreLine);
    public  boolean validateFile(String submittedFileName){
        if (submittedFileName.endsWith(this.format)){
            return true;
        }
        return false;
    }
    public String getFormat(){
        return this.format;
    }
}
