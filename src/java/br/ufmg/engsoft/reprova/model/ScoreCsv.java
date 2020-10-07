package br.ufmg.engsoft.reprova.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreCsv extends ScoreFile{
    public ScoreCsv() {
        super(".csv");
    }
    @Override
    public Course getScoredCourseFromFile(BufferedReader reader) throws IOException {
        Course course = null;
        List<Student> students = new ArrayList<Student>();
        boolean isHeader = true;
        try {
            while (reader.ready()) {
                String scoreLine = reader.readLine();
                //ignore csv header
                if(isHeader){
                    isHeader = false;
                    continue;
                }
                if (Objects.isNull(course)) {
                    course = courseFromLine(scoreLine);
                }
                Student student = studentFromLine(scoreLine);
                students.add(student);
            }
            Course scoredCourse = CourseFactory.create().createCourse(course.year,course.ref,course.courseName,students);
            return scoredCourse;
        }catch (Exception e){
            throw new IOException("Error reading file");
        }
    }

    public Course courseFromLine(String scoreLine) {
        String[] fields = parseLine(scoreLine);
        String courseName = fields[0];
        Integer year = Integer.parseInt(fields[1]);
        Integer referenceInt = Integer.parseInt(fields[2]);
        Course.Reference reference = Course.Reference.fromInt(referenceInt);
        CourseFactory courseFactory = CourseFactory.create();
        Course course = courseFactory.createCourse(year, reference, courseName);
        return course;
    }

    private String[] parseLine(String scoreLine) {
        String separator = "[,]+";
        String[] fields = scoreLine.split(separator);
        if (fields.length<5){
            throw new IllegalArgumentException("Csv must have 5 fields: courseName,courseYear,CourseReference,studentId,studentScore");
        }
        return fields;
    }

    public Student studentFromLine(String scoreLine) {
        String[] fields = parseLine(scoreLine);
        String id = fields[3];
        Float score = Float.parseFloat(fields[4]);
        Student student = new Student(id,score);
        return student;
    }


}
