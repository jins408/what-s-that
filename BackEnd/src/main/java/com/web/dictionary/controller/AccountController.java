package com.web.dictionary.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.dictionary.dto.User;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.IUserService;
import com.web.dictionary.service.JwtService;
import com.web.dictionary.util.SHA256Util;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/account")
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

	@Autowired JwtService jwtService;
	@Autowired IUserService userService;
	
	@ApiOperation(value = "회원정보 수정 (비밀번호, 프로필, 소개, 닉네임 변경 가능")
	@PutMapping(value = "/modify")
    public ResponseEntity<?> modifyUserInfo( @RequestBody User user) throws Exception{
		System.out.println("modify :" +user.toString());
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String token ="";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		System.out.println(userno);
		//기존의 유저정보
		User u = userService.getUserByUserno(userno);
		if(user.getPassword() != null) {
			String salt = u.getSalt();
			String password = SHA256Util.getEncrypt(user.getPassword(), salt);
			u.setPassword(password);
		}
		if(user.getIntroduce() != null) {
			u.setIntroduce(user.getIntroduce());
		}
		if(user.getProfile() != null) {
			u.setProfile(user.getProfile());
		}
		if(user.getUsername() != null) {
			u.setUsername(user.getUsername());;
		}
		System.out.println(u.toString());
		//이제 입력된 u를 update해줌
		if(userService.modifyUserInfo(u)) {
			result.status = true;
			result.message ="success";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message ="fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
    }
	
	@ApiOperation(value="회원탈퇴!")
	@DeleteMapping("/signout")
	public ResponseEntity<?> deleteAccount(){
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String token ="";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		if(userService.deleteAccount(userno)) {
			result.status = true;
			result.message = "success";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = true;
			result.message = "success";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
	}

}
