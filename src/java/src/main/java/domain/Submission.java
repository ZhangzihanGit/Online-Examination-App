package domain;

import db.SubmissionMapper;
import util.UnitOfWork;

import java.util.List;

public class Submission {
    private Integer id;
    private List<Answer> answers;
    private Integer mark;
    private Integer studentId;
    private Integer examId;

    public Submission() {

    }

    /**
     * The constructor is used when it's first time created.
     * @param studentId
     * @param examId
     */
    public Submission(int studentId, int examId ) {
        this.studentId = studentId;
        this.examId = examId;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    /**
     * This constructor is used when loading from DB. This should not be treated as a new
     * commit to the database, as it is the data loaded from DB.
     * @param id
     * @param answers
     * @param mark
     * @param studentId
     * @param examId
     */
    public Submission(int id, List<Answer> answers, int mark, int studentId, int examId) {
        this.id = id;
        this.answers = answers;
        this.mark = mark;
        this.studentId = studentId;
        this.examId = examId;

    }

    public int getExamId() {
        if (this.examId == null){
            load();
        }
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public int getStudentId() {
        if (this.studentId == null) {
            load();
        }
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        if (this.mark == null) {
            load();
        }
        return mark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMark(int mark) {
        this.mark = mark;
        UnitOfWork.getInstance().registerDirtyObject(this);
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
