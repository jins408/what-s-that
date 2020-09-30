package com.web.dictionary.service;


import com.web.dictionary.dto.Comment;
import java.util.List;

public interface ICommentService {

	//댓글 조회
	public List<Comment> getComments(int postno);
	//댓글 등록
	public int insertComment(Comment comment); // userno, reply, postno
	//댓글 삭제

	public int deleteComment(int regno);
}
