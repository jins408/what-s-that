package com.web.dictionary.service;

import com.web.dictionary.dao.CommentDao;
import com.web.dictionary.dto.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    CommentDao commentdao;

    @Override
    public List<Comment> getComments(int postno) throws Exception {
        return commentdao.getComments(postno);
    }

    @Override
    @Transactional
    public int insertComment(Comment comment) throws Exception {
        int userno = comment.getUserno();
        int postno = comment.getPostno();
        if(commentdao.registComment(userno, postno) == 1) { // insert -> null이면 성공, 실패면 e에러
            int regno = commentdao.getRegNo(userno, postno);
            comment.setCommentno(regno);
            return commentdao.insertComment(comment);
        } else {
            return 0;
        }
    }

    @Override
    public int deleteComment(int regno) throws Exception {
        return commentdao.deleteComment(regno);
    }

    @Override
    public boolean modifyComment(Comment comment) throws Exception {
        return commentdao.modifyComment(comment);
    }
}
