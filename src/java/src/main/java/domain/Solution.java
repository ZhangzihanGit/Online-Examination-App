package domain;

import util.UnitOfWork;

public class Solution {
    private int questionID;
    private String content;

    public Solution(int questionID, String content) {
        this.questionID = questionID;
        this.content = content;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getContent() {
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
}
