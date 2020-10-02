package com.web.dictionary.service;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

public interface IUserService {
	
	public int checkOverlapEmail(String email) throws Exception;

	public boolean saveAuthcode(String email, String code) throws Exception;

	public boolean signUp(SignupRequest request) throws Exception;

	public int checkEmailandCode(String email, String code) throws Exception;

	public void deleteAuthcode(String email) throws Exception;

	public int checkOverlapAuthcode(String email) throws Exception;

	public User logIn(String email, String password) throws Exception;

	public String getUserSalt(String email) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public boolean updatePassword(String email, String password) throws Exception;

	public User getUserByUsernoForModify(int userno) throws Exception;

	public boolean modifyUserInfo(User user) throws Exception;

	public boolean deleteAccount(int userno) throws Exception;

	public User getUserByUsernoForResponse(int userno) throws Exception;

}
