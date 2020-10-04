package com.web.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dictionary.dao.UserDao;
import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserDao userDao;

    public int checkOverlapEmail(String email) throws Exception {
        return userDao.checkOverlapEmail(email);
    }

    @Override
    public boolean saveAuthcode(String email, String code) throws Exception {
        return userDao.saveAuthcode(email, code);
    }

    @Override
    public boolean signUp(SignupRequest request) throws Exception {
        return userDao.signUp(request);
    }

    @Override
    public int checkEmailandCode(String email, String code) throws Exception {
        return userDao.checkEmailandCode(email, code);
    }

    @Override
    public void deleteAuthcode(String email) throws Exception {
        userDao.deleteAuthcode(email);
    }

    @Override
    public int checkOverlapAuthcode(String email) throws Exception {
        return userDao.checkOverlapAuthcode(email);
    }

    @Override
    public User logIn(String email, String password) throws Exception {
        return userDao.logIn(email, password);
    }

    @Override
    public String getUserSalt(String email) throws Exception {
        return userDao.getUserSalt(email);
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        return userDao.getUserByEmail(email);
    }

    @Override
    public boolean updatePassword(String email, String password) throws Exception {
        return userDao.updatePassword(email, password);
    }

    @Override
    public User getUserByUsernoForModify(int userno) throws Exception {
        return userDao.getUserByUsernoForModify(userno);
    }

    @Override
    public boolean modifyUserInfo(User user) throws Exception {
        return userDao.modifyUserInfo(user);
    }

    @Override
    public boolean deleteAccount(int userno) throws Exception {
        return userDao.deleteAccount(userno);
    }

    @Override
    public User getUserByUsernoForResponse(int userno) throws Exception {
        return userDao.getUserByUsernoForResponse(userno);
    }


}
