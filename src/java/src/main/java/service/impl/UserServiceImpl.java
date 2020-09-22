package service.impl;

import db.UserMapper;
import domain.Subject;
import domain.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    public List<Subject> viewAllSubjects(int userid){
        return null;
    }

    @Override
    public User getUser(String userName) {
        User user = UserMapper.loadWithUsername(userName);
        return user;
    }
}
