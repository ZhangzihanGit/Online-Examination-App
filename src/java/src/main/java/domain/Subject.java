package domain;

import java.util.List;

public class Subject {
    private String subjectCode;
    private List<Exam> exams;
    private List<Student> students;

    public Subject(String subjectCode, List<Exam> exams, List<Student> students) {
        this.subjectCode = subjectCode;
        this.exams = exams;
        this.students = students;
    }
}
