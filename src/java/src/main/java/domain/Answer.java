package domain;

import db.AnswerMapper;
import util.UnitOfWork;

import java.util.Date;

public class Answer {
    private int questionID;
    private String content;
    private Date updatedTime;

    public Answer(int questionID, String content, Date updatedTime) {
        this.questionID = questionID;
        this.content = content;
        this.updatedTime = updatedTime;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getContent() {
        if (this.content == null) {
            load();
        }
        return content;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setContent(String content) {
        this.content = content;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    private void load() {
        Answer answer = AnswerMapper.loadWithId(this.questionID);
        if (this.updatedTime == null) {
            this.updatedTime = answer.updatedTime;
        }
        if (this.content == null) {
            this.content = answer.content;
        }
    }
}
