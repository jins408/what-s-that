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

@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
@RestController
public class UserController {

	@Autowired EmailServiceImpl emailService;
	@Autowired IUserService userService;
	@Autowired JwtService jwtService;
	@Autowired KakaoAPI kakao;
	
	@ApiOperation(value = "이메일 중복 확인 -> 중복이 없으면 인증번호 이메일로 전송.")
	@GetMapping(value = "/emailoverlap/{email}")
    public ResponseEntity<?> checkOverlapEmail( @PathVariable ("email") String email) throws Exception{
		int IsOverlap = 0;
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
        IsOverlap = userService.checkOverlapEmail(email);
        // 1이라면 이메일 중복
        if(IsOverlap == 1){
        	System.out.println("중복");
        	result.status = false;
			result.data = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        else {
        	// 1이 아니라면 사용 가능
    		System.out.println(email);
    		//이메일 주소 받아서 코드 생성한 뒤 이메일에 코드를 보냄
    		String code = emailService.mailSend(email);
    		
    		if(code.equals("")) {
    			System.out.println("코드 생성 실패");
    			result.status = false;
    			result.data = "fail";
    			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    		}
    		else {
    			//email과 code를 DB에 저장함
    			//email에 해당되는 코드가 있으면 지우기 생성
    			if(userService.checkOverlapAuthcode(email) >= 1) {
    				userService.deleteAuthcode(email);
    			}
    			if(userService.saveAuthcode(email, code)) {
    				System.out.println("코드 등록 성공");
    				result.status = true;
    				result.data = "success";
    				return response = new ResponseEntity<>(result, HttpStatus.OK);
    			}
    			else {
    				System.out.println("코드 등록 실패");
    				result.status = false;
    				result.data = "fail";
    				return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    			}
    		}
        }
    }
	@ApiOperation(value = "이메일 인증 번호와 입력 번호를 비교")
	@GetMapping(value = "/emailcode/{email}/{code}")
	 public ResponseEntity<?> checkEmailCode( @PathVariable ("email") String email, @PathVariable ("code") String code ) throws Exception{
		ResponseEntity response = null;
		
		if(userService.checkEmailandCode(email,code)==1) {
			userService.deleteAuthcode(email);
			return response = new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	
	@ApiOperation(value = "회원 가입")
	@PostMapping(value = "/signup")
	 public ResponseEntity<?> signUp( @RequestBody SignupRequest request ) throws Exception{
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		
		//비밀번호 : salt값 생성 -> 입력한 비밀번호 + salt 합쳐서 SHA256(해싱) 해서 비밀번호로 저장
		String salt = SHA256Util.generateSalt();
		request.setSalt(salt);
		String newpwd = SHA256Util.getEncrypt(request.getPassword(), salt);
		request.setPassword(newpwd);
		
		if(userService.signUp(request)) {
			System.out.println("회원가입 성공");
			userService.deleteAuthcode(request.getEmail());
			result.status = true;
			result.data = "success";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			System.out.println("회원가입 실패");
			userService.deleteAuthcode(request.getEmail());
			result.status = false;
			result.data = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ApiOperation(value = "로그인")
	@GetMapping(value = "/login/{email}/{password}")
    public ResponseEntity<?> logIn(  @PathVariable ("email") String email, @PathVariable ("password") String password) throws Exception{
		ResponseEntity response = null;
		final BasicResponse result = new BasicResponse();
		String salt = userService.getUserSalt(email);
		String newpwd = SHA256Util.getEncrypt(password, salt);
        User u = userService.logIn(email,newpwd);
        if(u == null) {
        	System.out.println("ID/PW 틀림");
        	result.status = false;
			result.data = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
        	System.out.println("ID/PW 맞고, Token 발생하여 insert");
        	String token = jwtService.createToken(u.getUserno());
        	System.out.println(token);
        	u.setToken(token);
        	result.status = true;
			result.data = "success";
			result.object = u;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        
        
    }
	@ApiOperation(value = "로그인")
	@GetMapping(value = "/test")
	 public ResponseEntity<?> test() {
		ResponseEntity response = null;
		
		System.out.println("토큰 테스트");
		return response;
	}
	
	@ApiOperation(value = "카카오소셜로그인.")
	@GetMapping("/kakaologin")
	public ResponseEntity<?> kakaologin(@RequestParam("access_token") String access_token) throws Exception {
		ResponseEntity response = null;

		HashMap<String, Object> userInfo = kakao.getUserInfo(access_token);
		System.out.println("login Controller : " + userInfo);
		final BasicResponse result = new BasicResponse();
		// 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
		User u = userService.getUserByEmail((String) userInfo.get("email"));
		if (u != null) { // 중복된 에메일이 있으면
			String token = jwtService.createToken(u.getUserno());
			u.setToken(token);
			u.setPassword(null);
			result.object = u;
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			SignupRequest user = new SignupRequest();
			String email = (String) userInfo.get("email");
			String username = (String)userInfo.get("nickname");
			System.out.println("email : " + email);
			System.out.println("username : " + username);
			String pwd = access_token;
			String salt = SHA256Util.generateSalt();
			user.setEmail(email);
			user.setPassword(pwd);
			user.setUsername(username);
			user.setSalt(salt);

			userService.signUp(user); // 회원가입 완료

			u = userService.getUserByEmail(email);
			// 로그인 시작
			String token = jwtService.createToken(u.getUserno());
			u = new User();
			u.setToken(token);
			u.setPassword(null);
			result.object = u;
			result.status = true;
			result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

		return response;
	}
}
