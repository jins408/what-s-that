package com.web.dictionary.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.dictionary.dto.Culture;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.ICultureService;
import com.web.dictionary.service.JwtService;

import io.swagger.annotations.ApiOperation;


@RequestMapping("/culture")
@CrossOrigin(origins = { "*" })
@RestController
public class CultureController {

	
	@Autowired ICultureService cultureService;
	@Autowired JwtService jwtService;
	
	
	@ApiOperation(value = "문화재 상세페이지 조회 (문화재이름, 문화재내용, 찜 상태(1이면 찜한 상태)")
	@GetMapping(value = "/detail/{postno}")
    public ResponseEntity<?> getDetailCulturePost ( @PathVariable ("postno") int postno) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		String token = "";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		Culture culture = cultureService.getDetailCulturePost(postno,userno);
		if(culture != null) {
			result.status = true;
			result.message = "success";
			result.object = culture;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
		
	
	@ApiOperation(value = "문화재 이름으로 문화재 조회")
	@GetMapping(value = "/{culturename}")
    public ResponseEntity<?> getCultureInfoByCultureName( @PathVariable ("culturename") String culturename) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		Culture culture = cultureService.getCultureInfoByCultureName(culturename);
		if(culture != null) {
			result.status = true;
			result.message = "success";
			result.object = culture;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
	@ApiOperation(value = "문화재 이름으로 문화재 검색 (자동완성)")
	@GetMapping(value = "/search/{culturename}")
	public ResponseEntity<?> searchCultureInfoByCultureName( @PathVariable ("culturename") String culturename) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		List<String> list = cultureService.searchCultureInfoByCultureName(culturename);
		if(list != null) {
			result.status = true;
			result.message = "success";
			result.object = list;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "찜한 게시글 불러오기")
	@GetMapping(value = "/favorite")
	public ResponseEntity<?> getfavoritePost() throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		String token = "";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		System.out.println(userno);
		List<Culture> list = cultureService.getfavoritePost(userno);
		if(list != null) {
			result.status = true;
			result.message = "success";
			result.object = list;
			return response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			result.status = false;
			result.message = "fail";
			return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@ApiOperation(value = "찜 등록")
	@PostMapping(value = "/favorite")
	public ResponseEntity<?> registFavoriteCulture ( @RequestParam ("postno") int postno) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		String token = "";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		System.out.println(userno);
		if(cultureService.registFavoriteCulture(postno,userno)) {
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
	
	@ApiOperation(value = "찜 삭제")
	@DeleteMapping(value = "/favorite/{postno}")
	public ResponseEntity<?> deleteFavoriteCulture ( @PathVariable ("postno") int postno) throws Exception{
		ResponseEntity response = null;
		BasicResponse result = new BasicResponse();
		String token = "";
		Map<String, Object> payload = jwtService.getBody(token);
		int userno = (int)payload.get("userno");
		System.out.println(userno);
		if(cultureService.deleteFavoriteCulture(postno,userno)) {
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
			
    
}
