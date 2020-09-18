package com.web.dictionary.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.dictionary.dto.SignupRequest;

@Mapper
public interface UserDao {

	public int checkOverlapEmail(@Param("email") String email) throws Exception;

	public boolean saveAuthcode(@Param("email") String email, @Param("code") String code);

	public boolean signUp(SignupRequest request);

	public int checkEmailandCode(@Param("email") String email,@Param("code") String code);

	public void deleteAuthcode(@Param("email") String email);

	public int checkOverlapAuthcode(@Param("email") String email);


}
