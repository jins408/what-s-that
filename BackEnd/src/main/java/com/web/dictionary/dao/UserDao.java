package com.web.dictionary.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;

@Mapper
public interface UserDao {

    public int checkOverlapEmail(@Param("email") String email) throws Exception;

    public boolean saveAuthcode(@Param("email") String email, @Param("code") String code) throws Exception;

    public boolean signUp(SignupRequest request) throws Exception;

    public int checkEmailandCode(@Param("email") String email, @Param("code") String code) throws Exception;

    public void deleteAuthcode(@Param("email") String email) throws Exception;

    public int checkOverlapAuthcode(@Param("email") String email) throws Exception;

    public User logIn(@Param("email") String email, @Param("password") String password) throws Exception;

    public String getUserSalt(@Param("email") String email) throws Exception;

    public User getUserByEmail(@Param("email") String email) throws Exception;

    public boolean updatePassword(@Param("email") String email, @Param("password") String password) throws Exception;

    public User getUserByUsernoForModify(@Param("userno") int userno) throws Exception;

    public User getUserByUsernoForResponse(@Param("userno") int userno) throws Exception;

    public boolean modifyUserInfo(User user) throws Exception;

    public boolean deleteAccount(int userno) throws Exception;


}
