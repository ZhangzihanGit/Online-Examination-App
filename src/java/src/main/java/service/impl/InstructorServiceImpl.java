package service.impl;

import db.ExamMapper;
import db.InstructorMapper;
import db.SubjectMapper;
import domain.Exam;
import domain.Subject;
import domain.User;
import org.json.JSONObject;
import domain.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.InstructorService;
import service.UserService;
import util.UnitOfWork;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InstructorServiceImpl implements InstructorService {
    private static final Logger logger = LogManager.getLogger(InstructorServiceImpl.class);
    /**
     * Delete the exam given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     */
    @Override
    public void deleteExam(int subjectId, int examId) {

    }

    /**
     * Add a new exam given the subject ID. Exam id will be auto-generated
     * by the DB.
     *
//     * @param subjectId Subject Id
     */
    @Override
    public void addExam(Exam exam) {
        UnitOfWork.getInstance().commit();
    }
    @Override
    public void addQuestions(){
        UnitOfWork.getInstance().commit();
    }

    /**
     * View all exams under the subject.
     *
     * @param subjectId Subject Id
     * @return List of exams under the subject
     */
    @Override
    public List<Exam> viewAllExams(int subjectId) {
        return null;
    }

    /**
     * Retrieve the exam details given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     * @return Exam
     */
    @Override
    public Exam getExamById(int subjectId, int examId) {
        return null;
    }

    /**
     * Update the exam, given the subject Id and exam Id.
     *
     * @param subjectId Subject Id
     * @param examId    Exam Id
     */
    @Override
    public void updateExam(Exam exam) {
//        ExamMapper.updateExam(exam);
        UnitOfWork.getInstance().commit();
    }

    @Override
    public void updateQuestions(List<Question> questions) {
        for(Question q: questions) {
            UnitOfWork.getInstance().registerDirtyObject(q);
        }
        UnitOfWork.getInstance().commit();
    }

    /**
     * Update the marks of the student.
     *
     * @param studentId Student Id
     */
    @Override
    public void updateMarks(int studentId) {

    }

    /**
     * Close the exam.
     *
     * @param subjectId
     * @param examId
     */
    @Override
    public void closeExam(int userId, int examId) {
        try {
            Exam exam = ExamMapper.loadWithId(examId);
            ExamMapper.closeExam(exam);
        } catch (Exception e) {
            logger.error(e.getMessage());
            // TODO: error handling
        }
    }

    /**
     * Publish the exam and make it available to the student.
     *
     * @param subjectId
     * @param examId
     */
    @Override
    public void publishExam(int userId, int examId) {
        try {
            Exam exam = ExamMapper.loadWithId(examId);
            ExamMapper.publishExam(exam);
        }catch (Exception e) {
            logger.error(e.getMessage());
            // TODO: error handling needed.
        }

    }

    @Override
    public List<Subject> viewAllSubjects(int userId) {
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = SubjectMapper.loadInstructorSubjects(userId);
        return subjects;
    }

    @Override
    public List<Exam> viewAllExams(int subjectId, int userId, UserType userType) {
//        List<Exam> exams = ExamMapper.loadInstructorExam(subjectId, userId);
//        return exams;
        return null;
    }

    @Override
    public User getUser(String userName) {
        return null;
    }

    @Override
    public List<Student> viewAllStudents(int subjectId) {
        return null;
    }

    @Override
    public List<Instructor> viewAllInstructors(int subjectId) {
        List<Instructor> instructors = InstructorMapper.loadInstructorsBySubject(subjectId);
        return instructors;
    }

    @Override
    public Exam getExam(int examId) {
        return null;
    }
}
