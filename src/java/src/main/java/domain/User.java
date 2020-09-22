package domain;

import db.InstructorMapper;
import db.StudentMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.UnitOfWork;

import java.util.List;

import static domain.UserType.*;

public abstract class User {
    private static Logger logger = LogManager.getLogger(User.class);
    public Integer id;
    public String name;
    public UserType userType;
    public List<Subject> subjects;

    public User(){}
    
    public User(int id, String name, UserType userType, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.subjects = subjects;
    }

    public Integer getId() {
        if (id == null) {
            load();
        }
        return id;
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

    public void setId(Integer id) {
        this.id = id;
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
        // TODO: 有可能会有bug 具体连上数据库再测继承的效果（懒得本地mock测了）= =
        User user = StudentMapper.loadWithId(this.id)==null ?InstructorMapper.loadWithId(this.id):
                StudentMapper.loadWithId(this.id);
//        if (this.userType == null) {
//            this.userType = user.userType;
//        }
//        if (this.name == null) {
//            this.name = user.name;
//        }
//        if (this.subjects == null) {
//            this.subjects = user.subjects;
//        }
    }
}
