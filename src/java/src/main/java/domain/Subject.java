package domain;

import db.SubjectMapper;
import util.UnitOfWork;

import java.util.List;

public class Subject {
    private Integer id;
    private String subjectCode;
    private List<Exam> exams;
    private List<Student> students;

    public Subject(int id, String subjectCode, List<Exam> exams, List<Student> students) {
        this.subjectCode = subjectCode;
        this.exams = exams;
        this.students = students;
        this.id = id;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public String getSubjectCode() {
        if (this.subjectCode == null) {
            load();
        }
        return subjectCode;
    }

    public List<Exam> getExams() {
        if (this.exams == null) {
            load();
        }
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

    private void load() {
        Subject subject = SubjectMapper.loadWithId(this.subjectCode);
        if (this.exams == null) {
            this.exams = subject.exams;
        }
        if (this.students == null) {
            this.students = subject.students;
        }
        if (this.subjectCode == null) {
            this.subjectCode = subject.subjectCode;
        }
    }
}
