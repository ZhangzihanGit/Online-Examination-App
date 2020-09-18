package domain;

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

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
