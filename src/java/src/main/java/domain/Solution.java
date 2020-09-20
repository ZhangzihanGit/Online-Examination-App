package domain;

import db.SolutionMapper;
import util.UnitOfWork;

public class Solution {
    private Integer id;
    private Integer questionID;
    private String content;

    public Solution(int id, int questionID, String content) {
        this.questionID = questionID;
        this.content = content;
        this.id = id;
    }

    public int getQuestionID() {
        if (this.questionID == null) {
            load();
        }
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
        Solution solution = SolutionMapper.loadWithId(this.id);
        if (this.content == null) {
            this.content = solution.content;
        }
        if (this.questionID == null) {
            this.questionID = solution.questionID;
        }
    }
}
