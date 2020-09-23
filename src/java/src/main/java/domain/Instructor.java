package domain;

import db.InstructorMapper;
import db.StudentMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.UnitOfWork;

import java.util.List;

public class Instructor extends User{
    private static Logger logger = LogManager.getLogger(Instructor.class);
    public Instructor(){

    }
    public Instructor(int id, String name, List<Subject> subjects, UserType userType, String showName) {
        super(id,name,userType,subjects,showName);
    }
}
