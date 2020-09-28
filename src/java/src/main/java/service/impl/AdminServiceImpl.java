package service.impl;

import db.InstructorMapper;
import domain.Instructor;
import domain.Student;
import domain.Subject;
import service.AdminService;
import util.UnitOfWork;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public void addSubject(Subject subject) {
        UnitOfWork.getInstance().commit();
    }

    @Override
    public List<Instructor> viewAllInstructors() {
        List<Instructor> instructors = InstructorMapper.loadAllInstructors();
        return instructors;
    }

    @Override
    public List<Student> viewAllStudents() {
        return null;
    }
}
