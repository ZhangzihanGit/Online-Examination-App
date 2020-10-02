package service;

import domain.Instructor;
import domain.Student;
import domain.Subject;

import java.util.List;

public interface AdminService {
    public void addSubject();
    List<Instructor> viewAllInstructors();
    List<Student> viewAllStudents();
}
