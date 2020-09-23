package domain;

import db.ExamMapper;
import util.UnitOfWork;

import java.util.List;

public class Exam {
    private Integer subjectId;
    private String description;
    private Integer id;
    private boolean isPublished=false;
    private List<Question> questions;

    public Exam() {

    }
    public Exam(int subjectId, List<Question> questions,String description) {
        this.questions = questions;
        this.subjectId = subjectId;
        this.description = description;

        UnitOfWork.getInstance().registerNewObject(this);
    }
    public Exam(Integer id, Integer subjectId, String description, List<Question> questions,boolean isPublished) {
        this.description = description;
        this.questions = questions;
        this.id = id;
        this.isPublished = isPublished;
        this.subjectId = subjectId;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public void setId(Integer id) {
        this.id = id;
        UnitOfWork.getInstance().registerDeletedObject(this);
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        if (this.description == null) {
            load();
        }
        return description;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public int getSubjectId() {
        if (this.subjectId == null ){
            load();
        }
        return subjectId;
    }

    public List<Question> getQuestions() {
        if (this.questions == null) {
            load();
        }
        return questions;
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
            this.description = exam.getDescription();
        }
        if (this.questions == null) {
            this.questions = exam.getQuestions();
        }
        if (this.subjectId == null) {
            this.subjectId = exam.getSubjectId();
        }
    }
}
