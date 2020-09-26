package service.impl;

import domain.Subject;
import service.AdminService;
import util.UnitOfWork;

public class AdminServiceImpl implements AdminService {
    @Override
    public void addSubject(Subject subject) {
        UnitOfWork.getInstance().commit();
    }
}
