package com.web.dictionary.service;

import com.web.dictionary.dto.Culture;

import java.util.List;

public interface ICultureService {

    public List<Culture> getCultureInfoByCultureName(String culturename) throws Exception;

    public boolean registFavoriteCulture(int postno, int userno) throws Exception;

    public boolean deleteFavoriteCulture(int postno, int userno) throws Exception;

    public List<Culture> searchCultureInfoByCultureName(String culturename) throws Exception;

    public List<Culture> getfavoritePost(int userno) throws Exception;

    public Culture getDetailCulturePost(int postno, int userno) throws Exception;

    public int uploadPost(Culture post) throws Exception;

    public int updatePost(Culture post) throws Exception;

    public int deletePost(int postno) throws Exception;

    public int getCultureInfoByEName(String culturename) throws Exception;


}
