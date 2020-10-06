package com.web.dictionary.service;

import java.util.Map;

public interface JwtService {

    public boolean isUsable(String token);

    public Map<?, ?> getBody(String key);

    public String createToken(int userno);

    public Map<?, ?> getKey(String key);

}
