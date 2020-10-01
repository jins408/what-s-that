package com.web.dictionary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.dictionary.dto.Culture;
@Mapper
public interface CultureDao {

	public Culture getCultureInfoByCultureName(@Param("culturename") String culturename);

	public boolean registFavoriteCulture(@Param("postno") int postno, @Param("userno") int userno);

	public boolean deleteFavoriteCulture(@Param("postno") int postno, @Param("userno")int userno);

	public List<String> searchCultureInfoByCultureName(@Param("culturename") String culturename);

	public List<Culture> getfavoritePost( @Param("userno") int userno);

	public Culture getDetailCulturePost(@Param("postno") int postno, @Param("userno") int userno);
	
}
