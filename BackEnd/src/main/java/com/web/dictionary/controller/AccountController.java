package com.web.dictionary.controller;

import com.web.dictionary.dto.User;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.IUserService;
import com.web.dictionary.service.JwtService;
import com.web.dictionary.util.SHA256Util;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("/account")
@CrossOrigin(origins = {"*"})
@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    JwtService jwtService;
    @Autowired
    IUserService userService;

    @ApiOperation(value = "회원정보 수정 (비밀번호, 프로필, 소개, 닉네임 변경 가능) **formdata로 profile,password,introduce,userno 넘겨주기!!")
    @PutMapping(value = "/modify")
    public ResponseEntity<?> modifyUserInfo(@RequestPart(value = "profile", required = false) MultipartFile profile, @RequestParam("password") String password
            , @RequestParam("introduce") String introduce, @RequestParam("username") String username) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        Map m = jwtService.getKey("userno");
        int userno = (int) m.get("userno");
        //기존의 유저정보 not null인 부분만 null일시 기존데이터로 바꿔준다.
        User u = userService.getUserByUsernoForModify(userno);

        if (password != null && !password.equals("")) {
            String salt = u.getSalt();
            String newpassword = SHA256Util.getEncrypt(password, salt);
            u.setPassword(newpassword);
        }
        if (username != null && !username.equals("")) {
            u.setUsername(username);
        }
        if (introduce != null && !introduce.equals("")) {
            u.setIntroduce(introduce);
        }
        if (profile != null) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyMMddHHmmss");
            String time1 = format1.format(new Date());

//            String filename = "http://localhost:8080/dictionary/images/profile/" + userno + time1 + profile.getOriginalFilename();
            String filename = "http://j3b202.p.ssafy.io:8088/dictionary/images/profiles/" + userno + "_" + time1 + "_" + profile.getOriginalFilename();
            u.setProfileurl(filename);

//            String fileUrl = "/C://images/profile/" + userno + time1
//                    + profile.getOriginalFilename();
            String fileUrl = "/home/ubuntu/springboot/images/profiles/"
                    + userno + "_" + time1 + "_" + profile.getOriginalFilename();
            File dest = new File(fileUrl);
            profile.transferTo(dest);
        }
        //이제 입력된 u를 update해줌
        if (userService.modifyUserInfo(u)) {
            u.setPassword(null);
            u.setSalt(null);
            result.status = true;
            result.message = "success";
            result.object = u;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }


    @ApiOperation(value = "회원탈퇴!")
    @DeleteMapping("/signout")
    public ResponseEntity<?> deleteAccount() throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        Map m = jwtService.getKey("userno");
        int userno = (int) m.get("userno");
        if (userService.deleteAccount(userno)) {
            result.status = true;
            result.message = "success";
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

    }

    @ApiOperation(value = "회원정보 불러오기 (이메일, 닉네임, 프로필사진주소, 자기소개 ")
    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserByUsernoForResponse() throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        Map m = jwtService.getKey("userno");
        if (m.containsKey("userno")) {
            int userno = (int) m.get("userno");
            User u = userService.getUserByUsernoForResponse(userno);
            if (u != null) {
                u.setUserno(userno);
                result.status = true;
                result.message = "success";
                result.object = u;
                return response = new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                result.status = false;
                result.message = "fail";
                return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        } else {
            User u = new User();
            result.status = true;
            result.message = "success";
            result.object = u;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
    }


}
