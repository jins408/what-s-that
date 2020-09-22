package com.web.dictionary.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.EmailServiceImpl;
import com.web.dictionary.service.IUserService;
import com.web.dictionary.service.JwtService;
import com.web.dictionary.service.KakaoAPI;
import com.web.dictionary.util.SHA256Util;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/account")
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {


	

	
	
	
}
