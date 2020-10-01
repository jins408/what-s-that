package com.web.dictionary.service;

import com.web.dictionary.dao.PostDao;
import com.web.dictionary.dto.postVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostDao postDao;

    @Override
    public int uploadPost(postVo post) throws Exception {
        return postDao.uploadPost(post);
    }

    @Override
    public int updatePost(postVo post) throws Exception {
        return postDao.updatePost(post);
    }

    @Override
    public int deletePost(int postno) throws Exception {
        return postDao.deletePost(postno);
    }
}
