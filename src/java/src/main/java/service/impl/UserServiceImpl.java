package service.impl;

import db.ExamMapper;
import db.SubjectMapper;
import db.UserMapper;
import domain.Exam;
import domain.Subject;
import domain.User;
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
    public List<Exam> viewAllExams(int subjectId, int userId) {
        List<Exam> exams = ExamMapper.loadAllExams(subjectId, userId);
        return null;
    }

    @Override
    public User getUser(String userName) {
        User user = UserMapper.loadWithUsername(userName);
        return user;
    }
}
