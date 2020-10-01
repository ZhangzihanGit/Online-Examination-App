package domain;

import db.StudentMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.UnitOfWork;

import java.util.List;
import java.util.Map;

public class Student extends User{
    private static Logger logger = LogManager.getLogger(User.class);
    private List<Subject> subjects;
    private Boolean isInExam;
//    private List<Map<Exam,Integer>> marks;
    private String showName;

    public Student() {

    }
    public Student(int id, List<Subject> subjects, boolean isInExam, String name,
                   UserType userType, String showName) {
        super(id,name,userType,subjects,showName);
        this.isInExam = isInExam;
    }

    public boolean getInExam() {
        return isInExam;
    }

    public void setInExam(boolean inExam) {
        this.isInExam = inExam;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void delete() {
        UnitOfWork.getInstance().registerDeletedObject(this);
    }

    // Student may merely be used to load.
    @Override
    public void load() {
        super.load();
        logger.info("Reach Sub load, not only the parent load");
        Student student = null;
        try {
            student = StudentMapper.loadWithId(this.userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (this.isInExam == null) {
            this.isInExam = student.isInExam;
        }
    }
}
