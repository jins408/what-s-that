package com.web.dictionary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dictionary.dao.UserDao;
import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired UserDao dao;
	
	public int checkOverlapEmail(String email) throws Exception {
		return dao.checkOverlapEmail(email) ;
	}

	@Override
	public boolean saveAuthcode(String email, String code) {
		return dao.saveAuthcode(email,code);
	}

	@Override
	public boolean signUp(SignupRequest request) {
		return dao.signUp(request);
	}

	@Override
	public int checkEmailandCode(String email, String code) {
		return dao.checkEmailandCode(email,code);
	}

	@Override
	public void deleteAuthcode(String email) {
		dao.deleteAuthcode(email);
	}

	@Override
	public int checkOverlapAuthcode(String email) {
		return dao.checkOverlapAuthcode(email);
	}

	@Override
	public User logIn(String email, String password) {
		return dao.logIn(email,password);
	}

	@Override
	public String getUserSalt(String email) {
		return dao.getUserSalt(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return dao.getUserByEmail(email);
	}

	@Override
	public boolean updatePassword(String email, String password) {
		return dao.updatePassword(email, password);
	}

	@Override
	public User getUserByUserno(int userno) {
		return dao.getUserByUserno(userno);
	}

	@Override
	public boolean modifyUserInfo(User user) {
		return dao.modifyUserInfo(user);
	}

	@Override
	public boolean deleteAccount(int userno) {
		return dao.deleteAccount(userno);
	}

	
}
