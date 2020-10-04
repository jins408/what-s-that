package com.web.dictionary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.dictionary.dto.Culture;
@Mapper
public interface CultureDao {

	public Culture getCultureInfoByCultureName(@Param("culturename") String culturename) throws Exception;

	public boolean registFavoriteCulture(@Param("postno") int postno, @Param("userno") int userno) throws Exception;

	public boolean deleteFavoriteCulture(@Param("postno") int postno, @Param("userno")int userno) throws Exception;

	public List<String> searchCultureInfoByCultureName(@Param("culturename") String culturename) throws Exception;

	public List<Culture> getfavoritePost( @Param("userno") int userno) throws Exception;

	public Culture getDetailCulturePost(@Param("postno") int postno, @Param("userno") int userno) throws Exception;

	public int uploadPost(Culture post) throws Exception;

	public int updatePost(Culture post) throws Exception;

	public int deletePost(int postno) throws Exception;
}
