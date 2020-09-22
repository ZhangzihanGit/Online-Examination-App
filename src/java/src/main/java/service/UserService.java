package service;

import domain.Subject;

import java.util.List;

public interface UserService {
    List<Subject> viewAllSubjects(int userId);
}
