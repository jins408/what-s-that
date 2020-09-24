package com.web.dictionary.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	private int userno;
	private String username;
	private String email;
	private String password;
	private String profile;
	private String introduce;
	private String token;
	private String salt;
	
}
