package domain;

import com.google.gson.annotations.Expose;
import db.SubjectMapper;
import util.UnitOfWork;

import java.util.List;

public class Subject {
    @Expose(serialize = true,deserialize = true)
    private Integer id;
    @Expose(serialize = true,deserialize = true)
    private String subjectCode;
    @Expose(serialize = false,deserialize = false)
    private List<Exam> exams;
    @Expose(serialize = false,deserialize = false)
    private List<Student> students;
    @Expose(serialize = true,deserialize = true)
    private String description;

    public Subject() {
    }

    public Subject(int id, String description, String subjectCode, List<Exam> exams, List<Student> students) {
        this.subjectCode = subjectCode;
        this.exams = exams;
        this.students = students;
        this.id = id;
        this.description = description;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public void setDescription(String description) {
        this.description = description;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public String getDescription() {
        if (this.description == null) {
            load();
        }
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
        UnitOfWork.getInstance().registerDirtyObject(this);
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

    public Integer getId() {
        return id;
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
        if (this.description == null) {
            this.description = subject.description;
        }
    }
}
