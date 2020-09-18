package domain;

import util.UnitOfWork;

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

    public String getSubjectCode() {
        return subjectCode;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }
}
