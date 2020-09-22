package domain;

import db.ExamMapper;
import util.UnitOfWork;

import java.util.List;

public class Exam {
    private String subjectCode;
    private String description;
    private Integer id;
    private boolean isPublished=false;
    private List<Question> questions;

    public Exam() {

    }
    public Exam(Integer id,String subjectCode, String description, List<Question> questions,boolean isPublished) {
        this.subjectCode = subjectCode;
        this.description = description;
        this.questions = questions;
        this.id = id;
        this.isPublished = isPublished;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public String getSubjectCode() {
        if (this.subjectCode == null) {
            load();
        }
        return subjectCode;
    }

    public String getDescription() {
        if (this.description == null) {
            load();
        }
        return description;
    }

    public List<Question> getQuestions() {
        if (this.questions == null) {
            load();
        }
        return questions;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setDescription(String description) {
        this.description = description;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void deleteExam() {
        UnitOfWork.getInstance().registerDeletedObject(this);
    }

    private void load() {
        Exam exam = ExamMapper.loadWithId(this.id);
        if (this.description == null) {
            this.description = exam.description;
        }
        if (this.subjectCode == null) {
            this.subjectCode = exam.subjectCode;
        }
        if (this.questions == null) {
            this.questions = exam.questions;
        }
    }
}