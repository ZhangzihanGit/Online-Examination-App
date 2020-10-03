package domain;

import db.ExamMapper;
import util.UnitOfWork;

import java.util.List;

public class Exam {
    private Integer subjectId;
    private String description;
    private Integer examId;
    private boolean isPublished=false;
    private boolean isClosed=false;
    private List<Question> questions;
    private String showName;

    public Exam() {

    }
    public Exam(int subjectId, String showName, String description) {
        System.out.println("Exam partial constructor");
//        this.questions = questions;
        this.showName = showName;
        this.subjectId = subjectId;
        this.description = description;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public boolean isPublished() {
        return isPublished;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public Exam(Integer examId, Integer subjectId, String description, List<Question> questions, boolean isPublished,
                Boolean isClosed, String showName) {
        System.out.println("Exam full constructor");
        this.description = description;
        this.questions = questions;
        this.examId = examId;
        this.isPublished = isPublished;
        this.isClosed = isClosed;
        this.subjectId = subjectId;
        this.showName = showName;

    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setId(Integer id) {
        this.examId = id;
    }

    public Integer getId() {
        return examId;
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

    public void setPublished(Boolean published) {
        this.isPublished = published;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setClosed(Boolean closed) {
        this.isClosed = closed;
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
        // Update questions should not update the exam itself, because exam doesn't
        // have the reference of questions in DB.
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void deleteExam() {
        UnitOfWork.getInstance().registerDeletedObject(this);
    }

    private void load() {
        System.out.println("Exam is reloaded");
        Exam exam = ExamMapper.loadWithId(this.examId);
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
