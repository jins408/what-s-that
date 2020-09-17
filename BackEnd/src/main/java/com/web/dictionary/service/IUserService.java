package com.web.dictionary.service;

import com.web.dictionary.dto.SignupRequest;

public interface IUserService {
	
	public int checkOverlapEmail(String email) throws Exception;

	public boolean saveAuthcode(String email, String code);

	public boolean signUp(SignupRequest request);

	public int checkEmailandCode(String email, String code);

	public void deleteAuthcode(String email);

	public int checkOverlapAuthcode(String email);

}
