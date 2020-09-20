package domain;

import db.SubmissionMapper;
import util.UnitOfWork;

import java.util.List;

public class Submission {
    private Integer id;
    private List<Answer> answers;

    public Submission(int id, List<Answer> answers) {
        this.id = id;
        this.answers = answers;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    public int getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        if (this.answers == null) {
            load();
        }
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void deleteSubmission() {
        UnitOfWork.getInstance().registerDeletedObject(this);
    }

    private void load() {
        Submission submission = SubmissionMapper.loadWithId(this.id);
        if (this.answers == null) {
            this.answers = submission.answers;
        }
    }
}
