package com.web.dictionary.dao;

import com.web.dictionary.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {

    //댓글 조회
    public List<Comment> getComments(int postno);

    //댓글 삭제
    public int deleteComment(int commentno);
    
    //댓글 등록
    public int insertComment(Comment comment);
    public int registComment(@Param(value = "userno") int userno, @Param(value="postno") int postno);
    public int getRegNo(@Param(value = "userno") int userno, @Param(value="postno") int postno);

	public boolean modifyComment(Comment comment);


}
