package domain;

import java.util.List;

public class Exam {
    private String subjectCode;
    private String description;
    private List<Question> questions;

    public Exam(String subjectCode, String description, List<Question> questions) {
        this.subjectCode = subjectCode;
        this.description = description;
        this.questions = questions;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
