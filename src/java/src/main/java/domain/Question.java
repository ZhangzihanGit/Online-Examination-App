package domain;

import db.QuestionMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.UnitOfWork;

import java.util.List;
import java.util.Queue;

public class Question {
    private final static Logger logger = LogManager.getLogger(Question.class);
    private int questionID;
    private QuestionType questionType;
    private String description;
    private String options;
    private Integer examId;
    private Integer mark;
    public Question() {
    }

    /**
     * Overloading constructor, it is used when the object is first time created.
     * @param description
     * @param options
     * @param questionType
     * @param examId
     */
    public Question(String description, String options, QuestionType questionType, int examId,
                    int mark) {
        this.description = description;
        this.questionType = questionType;
        this.options = options;
        this.examId = examId;
        this.mark = mark;

        UnitOfWork.getInstance().registerNewObject(this);
    }

    /**
     * Full constructor, that requires question id. The contructor will not be used if the question
     * is the first time created.
     * @param questionID
     * @param description
     * @param options
     * @param questionType
     */
    public Question(int questionID, String description, String options, QuestionType questionType) {
        this.questionID = questionID;
        this.questionType = questionType;
        this.description = description;
        this.options = options;

//        UnitOfWork.getInstance().registerNewObject(this);
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getMark() {
        return mark;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public QuestionType getQuestionType() {
        if ( questionType ==null ){
            load();
        }
        return questionType;
    }

    public String getOptions() {
        if (options == null ) {
            load();
        }
        return options;
    }

    public Integer getExamId() {
        if (examId == null) {
            load();
        }
        return examId;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getDescription() {
        return description;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
//        UnitOfWork.getInstance().registerDirtyObject(this);
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
        logger.info("Loading questions...");
        Question question = QuestionMapper.loadWithId(this.questionID);
        if (options == null ){
            logger.info("Loading options");
            this.options = question.options;
        }
        if (questionType == null) {
            logger.info("Loading type");
            this.questionType = question.questionType;
        }
        if (description == null) {
            logger.info("loading description");
            this.description = question.description;
        }
        if (examId == null) {
            logger.info("loading examid");
            this.examId = examId;
        }

    }
}
