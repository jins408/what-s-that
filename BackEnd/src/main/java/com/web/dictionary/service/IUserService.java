package com.web.dictionary.service;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

public interface IUserService {
	
	public int checkOverlapEmail(String email) throws Exception;

	public boolean saveAuthcode(String email, String code);

	public boolean signUp(SignupRequest request);

	public int checkEmailandCode(String email, String code);

	public void deleteAuthcode(String email);

	public int checkOverlapAuthcode(String email);

	public User logIn(String email, String password);

	public String getUserSalt(String email);

	public User getUserByEmail(String email);

	public boolean updatePassword(String email, String password);

	public User getUserByUserno(int userno);

	public boolean modifyUserInfo(User user);

	public boolean deleteAccount(int userno);

}
