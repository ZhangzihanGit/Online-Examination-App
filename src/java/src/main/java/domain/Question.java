package domain;

import util.UnitOfWork;

public class Question {
    private int questionID;
    private String content;
    private String type;
    private String description;

    public Question(int questionID, String content, String type, String description) {
        this.questionID = questionID;
        this.content = content;
        this.type = type;
        this.description = description;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setContent(String content) {
        this.content = content;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setType(String type) {
        this.type = type;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setDescription(String description) {
        this.description = description;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }
}
