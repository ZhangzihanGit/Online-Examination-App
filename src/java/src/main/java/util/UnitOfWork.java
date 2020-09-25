package util;

import db.ExamMapper;
import db.QuestionMapper;
import domain.Exam;
import domain.Question;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class UnitOfWork {
    private static Logger logger = LogManager.getLogger(UnitOfWork.class);
    private static UnitOfWork instance;
    private ArrayList<Object> newObjectList = new ArrayList<Object>();
    private ArrayList<Object> dirtyObjectList = new ArrayList<Object>();
    private ArrayList<Object> deletedObjectList = new ArrayList<Object>();

    /**
     *
     * @return
     */
    public static UnitOfWork getInstance() {
        if (instance == null) {
            try {
                instance = new UnitOfWork();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     *
     * @param object
     */
    public void registerDirtyObject(Object object) {
        logger.info(object.getClass());
        if (newObjectList.contains(object) || dirtyObjectList.contains(object)
        || deletedObjectList.contains(object)) {
            return;
        }
        dirtyObjectList.add(object);
    }

    /**
     *
     * @param object
     */
    public void registerNewObject(Object object) {
        if (newObjectList.contains(object) || dirtyObjectList.contains(object)
                || deletedObjectList.contains(object)) {
            return;
        }
        newObjectList.add(object);
    }

    /**
     *
     * @param object
     */
    public void registerDeletedObject(Object object) {
        if(newObjectList.remove(object)) {
            return;
        }
        dirtyObjectList.remove(object);
        if(!deletedObjectList.contains(object)) {
            deletedObjectList.add(object);
        }
    }

    public void commit() {
        for ( int i=0; i<newObjectList.size(); i++) {
            Object object = newObjectList.get(i);
            if (object instanceof Exam) {
                logger.info("New exam is added");
                ExamMapper.addExam((Exam)object);
            }
            if (object instanceof Question) {
                logger.info("New question is added");
                QuestionMapper.addQuestion((Question) object);
            }
        }
        // Don't forget to clear the new objects after commit.
        newObjectList.clear();
        for ( int i=0; i<dirtyObjectList.size(); i++) {
            Object object = dirtyObjectList.get(i);
            if (object instanceof Exam) {
                logger.info("exam object is being updated with id: " + ((Exam) object).getId());
                ExamMapper.updateExam((Exam)object);
            }
            if (object instanceof Question) {
                logger.info("question object is being updated with id: "+((Question) object).getQuestionID());
                QuestionMapper.updateQuestion((Question) object);
            }
        }
        dirtyObjectList.clear();
        for (int i=0; i<deletedObjectList.size(); i++) {
            Object object = deletedObjectList.get(i);
            if (object instanceof Exam) {
                logger.info("exam object is deleted with id: " + ((Exam) object).getId());
                ExamMapper.deleteExam((Exam) object);
            }
            if (object instanceof Question) {
                logger.info("question object is deleted with id: " + ((Question) object).getQuestionID());
                QuestionMapper.deleteQuestion((Question) object);
            }
        }
        deletedObjectList.clear();
    }
}
