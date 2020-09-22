package domain;

import util.UnitOfWork;

import java.util.List;
import java.util.Queue;

public class Question {
    private int questionID;
    private QuestionType questionType;
    private String description;
    private String options;
    private Integer examId;
    public Question() {
    }

    public Question(String description, String options, QuestionType questionType) {
        this.description = description;
        this.questionType = questionType;
        this.options = options;
        this.examId = examId;
    }
    public Question(int questionID, String description, String options, QuestionType questionType) {
        this.questionID = questionID;
        this.questionType = questionType;
        this.description = description;
        this.options = options;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public int getQuestionID() {
        return questionID;
    }

    public QuestionType getType() {
        return questionType;
    }

    public String getDescription() {
        return description;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setOptions(String options) {
        this.options = options;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setType(QuestionType questionType) {
        this.questionType = questionType;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setDescription(String description) {
        this.description = description;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    private void load() {

    }
}
