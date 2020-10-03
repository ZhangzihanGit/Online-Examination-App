package util;

import domain.Exam;
import domain.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Registry {
    private final static Logger logger = LogManager.getLogger(Registry.class);
    private static Map<Student, Exam> studentExamMap = new HashMap<>();
    private static Registry instance;

    public static Registry getInstance() {
        if (instance == null ) {
            instance = new Registry();
        }
        return instance;
    }

    public void removeStudentExam(Student student) {
        if (checkStudentInExam(student)) {
            studentExamMap.remove(student);
        }
    }

    public void registerStartExamMap(Student student, Exam exam) {
        logger.info("the student with id: " + student.getUserId()+ " is in exam");
        studentExamMap.put(student,exam);
    }

    public boolean checkStudentInExam(Student student) {
        if(studentExamMap.containsKey(student)) {
            logger.info("student with id: "+ student.getUserId()+ " is in exam. ");
            return true;
        }
        else return false;
    }

    public Map<Student, Exam> getStartExamMap() {
        return studentExamMap;
    }
}
