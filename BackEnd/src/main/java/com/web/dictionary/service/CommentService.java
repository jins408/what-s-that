package com.web.dictionary.service;

import com.web.dictionary.dao.CommentDao;
import com.web.dictionary.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class CommentService implements ICommentService{

    @Autowired
    CommentDao commentdao;

    @Override
    public List<Comment> getComments(int postno) {
        return commentdao.getComments(postno);
    }

    @Override
    @Transactional
    public int insertComment(Comment comment) {
    	int userno = comment.getUserno();
    	int postno = comment.getPostno();
    	commentdao.registComment(userno, postno); // insert -> null이면 성공, 실패면 e에러
    	int regno = commentdao.getRegNo(userno, postno);
    	comment.setCommentno(regno);
        return commentdao.insertComment(comment);
    }


    @Override
    public int deleteComment(int regno) {
        return  commentdao.deleteComment(regno);
    }

	@Override
	public boolean modifyComment(Comment comment) {
		return commentdao.modifyComment(comment);
	}
}
