package com.web.dictionary.service;

import java.util.List;

import com.web.dictionary.dto.Culture;

public interface ICultureService {

    public Culture getCultureInfoByCultureName(String culturename) throws Exception;

    public boolean registFavoriteCulture(int postno, int userno) throws Exception;

    public boolean deleteFavoriteCulture(int postno, int userno) throws Exception;

    public List<String> searchCultureInfoByCultureName(String culturename) throws Exception;

    public List<Culture> getfavoritePost(int userno) throws Exception;

    public Culture getDetailCulturePost(int postno, int userno) throws Exception;

    public int uploadPost(Culture post) throws Exception;

    public int updatePost(Culture post) throws Exception;

    public int deletePost(int postno) throws Exception;

}
