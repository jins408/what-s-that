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
import org.springframework.web.bind.annotation.GetMapping;
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
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String token ="";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		System.out.println(userno);
		//기존의 유저정보 not null인 부분만 null일시 기존데이터로 바꿔준다.
		User u = userService.getUserByUsernoForModify(userno);
		System.out.println("front에서 보낸 회원정보 :" +user.toString());
		System.out.println("수정을 위해 불러온 회원정보 "+u.toString());
		if(!user.getPassword().equals("")) {
			String salt = u.getSalt();
			String password = SHA256Util.getEncrypt(user.getPassword(), salt);
			u.setPassword(password);
		}
		if(!user.getUsername().equals("")) {
			u.setUsername(user.getUsername());;
		}
		if(!user.getIntroduce().equals("")) {
			u.setIntroduce(user.getIntroduce());
		}
		if(!user.getProfile().equals("")) {
			u.setProfile(user.getProfile());
		}
		System.out.println("보정 후 회원정보 "+u.toString());
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
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
	}
	@ApiOperation(value="회원정보 불러오기 (이메일, 닉네임, 프로필사진주소, 자기소개 ")
	@GetMapping("/userinfo")
	public ResponseEntity<?> getUserByUsernoForResponse(){
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String token ="";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		User u = userService.getUserByUsernoForResponse(userno);
		if(u != null) {
			result.status = true;
			result.message = "success";
			result.object = u;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
	}
	

}
