package com.web.dictionary.service;

import com.web.dictionary.dto.postVo;

public interface IPostService {

    public int uploadPost(postVo post) throws Exception;
    public int updatePost(postVo post) throws Exception;
    public int deletePost(int postno) throws Exception;
}
