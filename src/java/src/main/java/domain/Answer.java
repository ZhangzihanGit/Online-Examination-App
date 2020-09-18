package domain;

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
        return content;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
