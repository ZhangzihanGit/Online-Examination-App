package domain;

import db.AnswerMapper;
import util.UnitOfWork;

import java.util.Date;

public class Answer {
    private Integer questionId;
    private Integer submissionId;
    private String content;
    private Integer id;
    private Integer mark;

    public Answer(){

    }

    public Answer(int questionId, String content, int submissionId) {
        this.questionId = questionId;
        this.content = content;
        this.submissionId = submissionId;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public Answer(int id, int questionId, String content,
                  int submissionId, int mark) {
        this.id = id;
        this.questionId = questionId;
        this.content = content;
        this.submissionId = submissionId;
        this.mark = mark;

//        UnitOfWork.getInstance().registerNewObject(this);
    }

    public Integer getMark() {
        return mark;
    }


    public void setMark(Integer mark) {
        this.mark = mark;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Integer getSubmissionId() {
        if (this.submissionId == null ) {
            load();
        }
        return submissionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public int getQuestionId() {
        if (this.questionId == null) {
            load();
        }
        return questionId;
    }

    public String getContent() {
        if (this.content == null) {
            load();
        }
        return content;
    }

    public void setQuestionID(int questionId) {
        this.questionId = questionId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setContent(String content) {
        this.content = content;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    private void load() {
        Answer answer = AnswerMapper.loadWithId(id);

        if (this.content == null) {
            this.content = answer.content;
        }
        if (this.questionId == null) {
            this.questionId = answer.questionId;
        }
        if (this.submissionId == null) {
            this.submissionId = answer.submissionId;
        }
    }
}
