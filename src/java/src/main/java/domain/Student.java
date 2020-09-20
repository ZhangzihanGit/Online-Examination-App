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
    private boolean isInExam;
    private List<Map<Exam,Integer>> marks;

    public Student(int id, List<Subject> subjects, boolean isInExam, String name,
                   UserType userType) {
        super(id,name,userType,subjects);
        this.isInExam = isInExam;
    }

    public boolean getInExam() {
        return isInExam;
    }

    public List<Map<Exam, Integer>> getMarks() {
        if (marks == null) {
            load();
        }
        return marks;
    }

    public void setInExam(boolean inExam) {
        this.isInExam = inExam;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setMarks(List<Map<Exam, Integer>> marks) {
        this.marks = marks;
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
        Student student = StudentMapper.loadWithId(this.id);
        if (this.marks == null) {
//            this.marks = student.marks;
            logger.info("marks is null");
        }
    }
}
