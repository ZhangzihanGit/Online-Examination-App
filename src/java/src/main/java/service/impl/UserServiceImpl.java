package service.impl;

import db.ExamMapper;
import db.SubjectMapper;
import db.UserMapper;
import domain.*;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    /**
     *
     * @param userid this id actually not used
     * @return
     */
    public List<Subject> viewAllSubjects(int userid){
        List<Subject> subjects = new ArrayList<Subject>();
        subjects = SubjectMapper.loadAllSubjects();
        return subjects;
    }

    @Override
    public List<Exam> viewAllExams(int subjectId, int userId, UserType userType) {
        List<Exam> exams = ExamMapper.loadAllExams(subjectId, userId, userType);
        return exams;
    }

    @Override
    public User getUser(String userName) {
        User user = UserMapper.loadWithUsername(userName);
        return user;
    }

    @Override
    public List<Student> viewAllStudents(int subjectId) {
        return null;
    }

    @Override
    public List<Instructor> viewAllInstructors(int subjectId) {
        return null;
    }

    @Override
    public Exam getExam(int examId) {
        Exam exam = ExamMapper.loadWithId(examId);
        return exam;
    }
}
