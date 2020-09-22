package service.impl;

import db.SubjectMapper;
import domain.Exam;
import domain.Subject;
import domain.User;
import service.InstructorService;
import service.UserService;
import util.UnitOfWork;

import java.util.ArrayList;
import java.util.List;

public class InstructorServiceImpl implements InstructorService {
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
    public void updateExam(int subjectId, int examId) {

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
    public void closeExam(int subjectId, int examId) {

    }

    /**
     * Publish the exam and make it available to the student.
     *
     * @param subjectId
     * @param examId
     */
    @Override
    public void publishExam(int subjectId, int examId) {

    }

    @Override
    public List<Subject> viewAllSubjects(int userId) {
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = SubjectMapper.loadInstructorSubjects(userId);
        return subjects;
    }

    @Override
    public User getUser(String userName) {
        return null;
    }
}
