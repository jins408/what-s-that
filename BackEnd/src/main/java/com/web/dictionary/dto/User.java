package com.web.dictionary.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
	private int userno;
	@Setter
	private String username;
	private String email;
	@Setter
	private String password;
	@Setter
	private String profile;
	@Setter
	private String introduce;
	@Setter
	private String token;
	
	
	
}
