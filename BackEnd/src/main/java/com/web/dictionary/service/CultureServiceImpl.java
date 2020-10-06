package com.web.dictionary.service;

import com.web.dictionary.dao.CultureDao;
import com.web.dictionary.dto.Culture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CultureServiceImpl implements ICultureService{

	
	@Autowired CultureDao cultureDao;
	
	@Override
	public List<Culture> getCultureInfoByCultureName(String culturename) throws Exception {
		return cultureDao.getCultureInfoByCultureName(culturename);
	}

	@Override
	public boolean registFavoriteCulture(int postno, int userno) throws Exception {
		return cultureDao.registFavoriteCulture(postno, userno);
	}

	@Override
	public boolean deleteFavoriteCulture(int postno, int userno) throws Exception {
		return cultureDao.deleteFavoriteCulture(postno,userno);
	}

	@Override
	public List<Culture> searchCultureInfoByCultureName(String culturename) throws Exception {
		return cultureDao.searchCultureInfoByCultureName(culturename);
	}

	@Override
	public List<Culture> getfavoritePost(int userno) throws Exception {
		return cultureDao.getfavoritePost(userno);
	}

	@Override
	public Culture getDetailCulturePost(int postno, int userno) throws Exception {
		return cultureDao.getDetailCulturePost(postno,userno);
	}

	@Override
	public int uploadPost(Culture post) throws Exception {
		return cultureDao.uploadPost(post);
	}

	@Override
	public int updatePost(Culture post) throws Exception {
		return cultureDao.updatePost(post);
	}

	@Override
	public int deletePost(int postno) throws Exception {
		return cultureDao.deletePost(postno);
	}

	@Override
	public int getCultureInfoByEName(String culturename) throws Exception {
		return cultureDao.getCultureInfoByEName(culturename);
	}

}
