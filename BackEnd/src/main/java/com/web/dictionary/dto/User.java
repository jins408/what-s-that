package com.web.dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int userno;
	private String username;
	private String email;
	private String password;
//	MultipartFile profile;
	private String profileurl;
	private String introduce;
	private String token;
	private String salt;
	private int isadmin;
}
