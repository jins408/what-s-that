package com.web.dictionary.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

@Mapper
public interface UserDao {

	public int checkOverlapEmail(@Param("email") String email) throws Exception;

	public boolean saveAuthcode(@Param("email") String email, @Param("code") String code);

	public boolean signUp(SignupRequest request);

	public int checkEmailandCode(@Param("email") String email,@Param("code") String code);

	public void deleteAuthcode(@Param("email") String email);

	public int checkOverlapAuthcode(@Param("email") String email);

	public User logIn(@Param("email") String email, @Param("password") String password);

	public String getUserSalt(@Param("email") String email);

	public User getUserByEmail(@Param("email") String email);

	public boolean updatePassword(@Param("email") String email, @Param("password") String password);

	public User getUserByUserno(@Param("userno") int userno);

	public boolean modifyUserInfo(User user);

	public boolean deleteAccount(int userno);


}
