package service;

import domain.Subject;
import domain.User;

import java.util.List;

public interface UserService {
    List<Subject> viewAllSubjects(int userId);
    User getUser(String userName);
}
