package com.web.dictionary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import web.com.dictionary.dto.User;
import web.com.dictionary.service.EmailServiceImpl;

@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
@Controller
public class UserController {

	@Autowired EmailServiceImpl emailService;
	
	@GetMapping(value = "/authemail/{email}")
    public ResponseEntity<?> authemail( User user,  @PathVariable ("email") String email){
        user = emailService.authemail(email);
        System.out.println(user);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
