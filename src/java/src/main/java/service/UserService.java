package service;

import domain.Exam;
import domain.Subject;
import domain.User;
import domain.UserType;

import java.util.List;

public interface UserService {
    List<Subject> viewAllSubjects(int userId);
    List<Exam> viewAllExams(int subjectId, int userId, UserType userType);
    User getUser(String userName);
}
