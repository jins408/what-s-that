package com.web.dictionary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dictionary.dao.CultureDao;
import com.web.dictionary.dto.Culture;

@Service
public class CultureServiceImpl implements ICultureService{

	
	@Autowired CultureDao dao;
	
	@Override
	public Culture getCultureInfoByCultureName(String culturename) {
		return dao.getCultureInfoByCultureName(culturename);
	}

	@Override
	public boolean registFavoriteCulture(int postno, int userno) {
		return dao.registFavoriteCulture(postno, userno);
	}

	@Override
	public boolean deleteFavoriteCulture(int postno, int userno) {
		return dao.deleteFavoriteCulture(postno,userno);
	}

	@Override
	public List<String> searchCultureInfoByCultureName(String culturename) {
		return dao.searchCultureInfoByCultureName(culturename);
	}

	@Override
	public List<Culture> getfavoritePost(int userno) {
		return dao.getfavoritePost(userno);
	}

	@Override
	public Culture getDetailCulturePost(int postno, int userno) {
		return dao.getDetailCulturePost(postno,userno);
	}

}
