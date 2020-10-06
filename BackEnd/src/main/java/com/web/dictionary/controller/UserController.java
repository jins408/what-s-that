package com.web.dictionary.controller;

import com.web.dictionary.dto.SignupRequest;
import com.web.dictionary.dto.User;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.EmailServiceImpl;
import com.web.dictionary.service.IUserService;
import com.web.dictionary.service.JwtService;
import com.web.dictionary.service.KakaoAPI;
import com.web.dictionary.util.SHA256Util;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
//로그인이 필요없는 과정들!!!!!!
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired EmailServiceImpl emailService;
	@Autowired IUserService userService;
	@Autowired JwtService jwtService;
	@Autowired KakaoAPI kakao;
	
	@ApiOperation(value = "이메일 중복 확인 -> 중복이 없으면 인증번호 이메일로 전송.")
	@GetMapping(value = "/emailoverlap/{email}")
    public ResponseEntity<?> checkOverlapEmail( @PathVariable ("email") String email) throws Exception{
		int IsOverlap = 0;
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
        IsOverlap = userService.checkOverlapEmail(email);
        // 1이라면 이메일 중복
        if(IsOverlap == 1){
        	logger.info("중복");
        	result.status = true;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
        	// 1이 아니라면 사용 가능
			logger.info(email);
    		//이메일 주소 받아서 코드 생성한 뒤 이메일에 코드를 보냄
    		String code = emailService.mailSend(email);
    		
    		if(code.equals("")) {
				logger.info("코드 생성 실패");
    			result.status = false;
    			result.message = "fail";
    			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    		}
    		else {
    			//email과 code를 DB에 저장함
    			//email에 해당되는 코드가 있으면 지우기 생성
    			if(userService.checkOverlapAuthcode(email) >= 1) {
    				userService.deleteAuthcode(email);
    			}
    			if(userService.saveAuthcode(email, code)) {
    				logger.info("코드 등록 성공");
    				result.status = true;
    				result.message = "success";
    				return response = new ResponseEntity<>(result, HttpStatus.OK);
    			}
    			else {
					logger.info("코드 등록 실패");
    				result.status = false;
    				result.message = "fail";
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

	@ApiOperation(value = "회원 가입 (email, password, username 필수! ")
	@PostMapping(value = "/signup")
	 public ResponseEntity<?> signUp( @RequestBody SignupRequest request ) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		
		//비밀번호 : salt값 생성 -> 입력한 비밀번호 + salt 합쳐서 SHA256(해싱) 해서 비밀번호로 저장
		String salt = SHA256Util.generateSalt();
		request.setSalt(salt);
		String newpwd = SHA256Util.getEncrypt(request.getPassword(), salt);
		request.setPassword(newpwd);
		
		if(userService.signUp(request)) {
			logger.info("회원가입 성공");
			userService.deleteAuthcode(request.getEmail());
			result.status = true;
			result.message = "success";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			logger.info("회원가입 실패");
			userService.deleteAuthcode(request.getEmail());
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ApiOperation(value = "로그인")
	@PostMapping(value = "/login")
    public ResponseEntity<?> logIn(@RequestBody User user) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		String salt = userService.getUserSalt(user.getEmail());
		User u = null;
		if(salt != null) {
			String newpwd = SHA256Util.getEncrypt(user.getPassword(), salt);
			u = userService.logIn(user.getEmail(), newpwd);
		}
        if(u == null) {
			logger.info("ID/PW 틀림");
        	result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        else {
			logger.info("ID/PW 맞고, Token 발생하여 insert");
        	String token = jwtService.createToken(u.getUserno());
			logger.info(token);
        	u.setToken(token);
        	result.status = true;
			result.message = "success";
			result.object = u;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        
    }

	
	@ApiOperation(value = "카카오소셜로그인.")
	@GetMapping("/kakaologin")
	public ResponseEntity<?> kakaologin(@RequestParam("access_token") String access_token) throws Exception {
		ResponseEntity response = null;

		HashMap<String, Object> userInfo = kakao.getUserInfo(access_token);
		logger.info("login Controller : " + userInfo);
		BasicResponse result = new BasicResponse();
		// 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
		User u = userService.getUserByEmail((String) userInfo.get("email"));
		if (u != null) { // 중복된 에메일이 있으면
			String token = jwtService.createToken(u.getUserno());
			u.setToken(token);
			u.setPassword(null);
			result.object = u;
			result.status = true;
			result.message = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			SignupRequest user = new SignupRequest();
			String email = (String) userInfo.get("email");
			String username = (String)userInfo.get("nickname");
			logger.info("email : " + email);
			logger.info("username : " + username);
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
			result.message = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}

		return response;
	}
	@ApiOperation(value = "비밀번호 찾기 (이메일 존재 확인 -> 새로운 비밀번호 전송 -> 새로운 비밀번호 update")
	@GetMapping(value = "/findpwd/{email}/")
    public ResponseEntity<?> findPwd(  @PathVariable ("email") String email) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		//이메일이 존재하는지 확인 -> 새로운 비밀번호를 만들어서  update 후  -> 이메일로 새로운 비밀번호 전송
		int IsOverlap = 0; 
		IsOverlap = userService.checkOverlapEmail(email);
		//해당 이메일이 존재
		if(IsOverlap == 1) {
			// 새로운 비밀번호룰 만들어서 salt값으로 암호화 한 후 update
			String password = emailService.passwordSend(email);
			String salt = userService.getUserSalt(email);
			String newpassword = SHA256Util.getEncrypt(password, salt);
			if(userService.updatePassword(email, newpassword)) {
				logger.info("비밀번호 update 성공");
	        	result.status = true;
				result.message = "success";
				return response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				logger.info("비밀번호 update 실패");
				result.status = false;
				result.message = "fail";
				return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		}
		else {
			logger.info("입력 이메일을 확인해주세요");
        	result.status = true;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
        
    }
}
