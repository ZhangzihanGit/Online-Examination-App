package util;

import domain.Instructor;
import domain.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Test {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Test.class);
        Instructor instructor = new Instructor(1,"test",null,null,null);
//        System.out.println(instructor.getId());
//        logger.info(instructor.getId());
//        instructor.setId(1000);
//        instructor.setName("Hello");
//        logger.info(instructor.getId());
        Student student = new Student(1,null,false,"test",null,null);
        student.setUserId(10);
        student.setUserId(null);
        student.getUserId();
    }
}
