package domain;

import db.InstructorMapper;
import db.StudentMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.UnitOfWork;

import java.util.List;

public abstract class User {
    private static Logger logger = LogManager.getLogger(User.class);
    public Integer userId;
    public String name;
    public UserType userType;
    public List<Subject> subjects;
    public String showName;

    public User(){}

    public User(int userId, String name, UserType userType, List<Subject> subjects, String showName) {
        this.userId = userId;
        this.name = name;
        this.userType = userType;
        this.subjects = subjects;
        this.showName = showName;
    }

    public Integer getUserId() {
        if (userId == null) {
            load();
        }
        return userId;
    }

    public String getName() {
        if (name==null) {
            load();
        }
        return name;
    }

    public UserType getUserType() {
        if (userType == null) {
            load();
        }
        return userType;
    }

    public List<Subject> getSubjects() {
        if (subjects == null) {
            load();
        }
        return subjects;
    }

    public String getShowName () {
        if (showName==null) {
            load();
        }
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setName(String name) {
        this.name = name;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        UnitOfWork.getInstance().registerDirtyObject(this);
    }

    public void load() {
        logger.info("Reach the parent loader");
        User user = StudentMapper.loadWithId(this.userId)==null ?InstructorMapper.loadWithId(this.userId):
                StudentMapper.loadWithId(this.userId);

    }
}
