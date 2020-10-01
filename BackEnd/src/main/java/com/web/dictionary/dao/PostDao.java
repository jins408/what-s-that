package com.web.dictionary.dao;

import com.web.dictionary.dto.postVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface PostDao {

    public int uploadPost(postVo post) throws Exception;

    public int updatePost(postVo post) throws Exception;

    public int deletePost(int postno) throws Exception;
}
