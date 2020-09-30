package com.web.dictionary.controller;

import com.web.dictionary.dao.CommentDao;
import com.web.dictionary.dto.Comment;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.ICommentService;
import com.web.dictionary.util.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ICommentService commentservice;

    @GetMapping("/{postno}")
    public ResponseEntity<?> getComments(@PathVariable("postno") int postno){
        BasicResponse result = new BasicResponse();
        List<Comment> commentList = commentservice.getComments(postno);
          if(commentList.size() > 0 ){
                result.object = commentList;
                result.status = true;
              return new ResponseEntity(result, HttpStatus.OK);
          } else{
              return new ResponseEntity(result, HttpStatus.NO_CONTENT);
          }

    }
    
    @DeleteMapping("/{regno}")
    public ResponseEntity<?> deleteComment(@PathVariable("regno") int regno){
        BasicResponse result = new BasicResponse();
        int res = commentservice.deleteComment(regno);
        if( res == 1) { //성공
        	result.status = true;
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
        	result.status = false;
        	return new ResponseEntity(result, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @PostMapping("")
    public ResponseEntity<?> insertComment(Comment commnet){
        BasicResponse result = new BasicResponse();

        commentservice.insertComment(commnet);
        
        return new ResponseEntity(result, HttpStatus.OK);
    
    
    }
    


}
