package service;

import domain.Subject;

import java.util.List;

public interface UserService {
    public List<Subject> viewAllSubjects(int userId);
    public String getUserType(String userName);
}
