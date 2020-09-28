package service;

import domain.*;

import java.util.List;

public interface UserService {
    List<Subject> viewAllSubjects(int userId);
    List<Exam> viewAllExams(int subjectId, int userId, UserType userType);
    User getUser(String userName);
    User getUser(int userId);
    List<Student> viewAllStudents(int subjectId);
    List<Instructor> viewAllInstructors(int subjectId);
    Exam getExam(int examId);
}
