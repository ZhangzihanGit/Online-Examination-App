package util;

import db.StudentMapper;
import domain.Exam;
import domain.Student;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registry {
    private final static Logger logger = LogManager.getLogger(Registry.class);
    // mapping: <studentId, [examId]>
    private static Map<Integer, List<Integer>> studentExamMap = new HashMap<>();
    private static Registry instance;

    public static Registry getInstance() {
        if (instance == null ) {
            instance = new Registry();
            initMap();
        }
        return instance;
    }


    private static void initMap() {
        studentExamMap  = StudentMapper.loadTakingExams();
    }

    public void removeStudentExam(int studentId, int examId) {
        if (checkStudentInExam(studentId, examId)) {
            studentExamMap.remove(studentId);
        }
    }

    public void registerStartExamMap(Student student, Exam exam) {
        logger.info("the student with id: " + student.getUserId()+ " is in exam");
        studentExamMap.get(student.getUserId()).add(exam.getId());
        StudentMapper.updateTakingExam(student.getUserId(), studentExamMap.get(student.getUserId()));

    }

    public boolean checkStudentInExam(int studentId, int examId) {
        // if we have the student's id AND has the examId registered
        // then the student it taking the exam
        if(studentExamMap.containsKey(studentId) &&
                studentExamMap.get(studentId).contains(examId)) {
            logger.info("student with id: "+ studentId + " is in exam. ");
            return true;
        }
        else return false;
    }

    public Map<Integer, List<Integer>> getStartExamMap() {
        return studentExamMap;
    }
}
