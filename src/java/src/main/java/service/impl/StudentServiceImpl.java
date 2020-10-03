package service.impl;

import db.ExamMapper;
import db.StudentMapper;
import db.SubjectMapper;
import domain.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.StudentService;
import service.UserService;
import util.Registry;
import util.UnitOfWork;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

    @Override
    public void addSubmission(Submission submission) {
        UnitOfWork.getInstance().commit();
    }
    @Override
    public Exam getExam(int examId) {
        return null;
    }

    /**
     * Update the answer given the subject ID, exam Id and questions ID.
     *
     * @param subjectId  Subject
     * @param examId     Exam that the question is in
     * @param questionId The question that needs to be updated
     * @param content    New content to be updated
     */
    @Override
    public void updateAnswer(int subjectId, int examId, int questionId, String content) {

    }

    /**
     * Submit the exam, given the subject and exam IDs.
     *
     * @param subjectId Subject
     * @param examId    Exam that needs to be submitted
     */
    @Override
    public void submitExam(int subjectId, int examId) {

    }

    /**
     * Initialisation when the exam begins, Return all questions to front end.
     *
     * @param subjectId Subject
     * @param examId    Exam
     * @return List of all questions in one exam.
     */
    @Override
    public List<Question> viewAllQuestions(int subjectId, int examId) {
        return null;
    }

    /**
     * View all exams under the subject.
     *
     * @param subjectId Subject
     * @return List of all exams under the subject
     */
    @Override
    public List<Exam> viewAllExams(int subjectId) {
        return null;
    }

    /**
     * View the exam given the exam ID.
     *
     * @param subjectId Subject
     * @param examId    Exam
     * @return One exam
     */
    @Override
    public Exam viewExamById(int subjectId, int examId) {
        return null;
    }

    @Override
    public List<Subject> viewAllSubjects(int userId) {
        List<Subject> subjects = new ArrayList<>();
        subjects = SubjectMapper.loadStudentSubjects(userId);
        return subjects;
    }

    @Override
    public List<Exam> viewAllExams(int subjectId, int userId, UserType userType) {
//        List<Exam> exams = ExamMapper.loadStudentExams(subjectId, userId);
//        return exams;
        return null;
    }

    @Override
    public User getUser(String userName) {
        return null;
    }

    @Override
    public User getUser(int userId) {
        Student student = StudentMapper.loadWithId(userId);
        return student;
    }

    @Override
    public List<Student> viewAllStudents(int subjectId) {
        List<Student> students = StudentMapper.loadStudentsBySubject(subjectId);
        return students;
    }

    @Override
    public List<Instructor> viewAllInstructors(int subjectId) {
        return null;
    }

    @Override
    public boolean startExam(Student student, Exam exam) {
        // Change student attribute to true
        student.setInExam(true);
        // Register the student taking the exam.
        Registry.getInstance().registerStartExamMap(student, exam);
        return StudentMapper.updateStatus(student);
    }

    public boolean endExam(Student student) {
        student.setInExam(false);
        return StudentMapper.updateStatus(student);
    }

    @Override
    public void addSubmission(int examId, int userId) {
        UnitOfWork.getInstance().commit();
        Exam exam = ExamMapper.loadWithId(examId);
        Student student = StudentMapper.loadWithId(userId);
        Registry.getInstance().registerStartExamMap(student,exam);
    }

    @Override
    public void addAnswer(){
        UnitOfWork.getInstance().commit();
    }

}
