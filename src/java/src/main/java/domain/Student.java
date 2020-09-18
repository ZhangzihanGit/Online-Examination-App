package domain;

import db.StudentMapper;
import util.UnitOfWork;

import java.util.List;
import java.util.Map;

public class Student extends User{
    private List<Subject> subjects;
    private boolean status;
    private List<Map<Exam,Integer>> marks;

    public Student(int id, List<Subject> subjects, boolean status, String name,
                   UserType userType) {
        super(id,name,userType,subjects);
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public List<Map<Exam, Integer>> getMarks() {
        if (marks == null) {
            load();
        }
        return marks;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
        Student student = StudentMapper.loadWithId(this.id);
        if (this.marks == null) {
            this.marks = student.marks;
        }
    }
}
