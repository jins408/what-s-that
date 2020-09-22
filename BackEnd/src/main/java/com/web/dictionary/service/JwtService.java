package com.web.dictionary.service;

import java.util.Map;

public interface JwtService {

	public boolean isUsable(String token);
	public Map<String, Object> getBody(String key);
	public String createToken(int userno);

}
