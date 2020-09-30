package com.web.dictionary.service;

import java.util.List;

import com.web.dictionary.dto.Culture;

public interface ICultureService {

	public Culture getCultureInfoByCultureName(String culturename);

	public boolean registFavoriteCulture(int postno, int userno);

	public boolean deleteFavoriteCulture(int postno, int userno);

	public List<String> searchCultureInfoByCultureName(String culturename);

	public List<Culture> getfavoritePost(int userno);
	
}
