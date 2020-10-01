package com.web.dictionary.controller;

import com.web.dictionary.dto.postVo;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/post")
@CrossOrigin(origins = {"*"})
@RestController
public class PostController {

    @Autowired
    IPostService postService;

    @ApiOperation(value = "문화재 게시물 생성")
    @PostMapping(value = "")
    public ResponseEntity<?> uploadPost(@RequestPart("image") MultipartFile imgfile,
                                        @RequestParam("culturename") String culturename,
                                        @RequestParam("content") String content) throws Exception {
        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
        String filename = "http://localhost:8080/dictionary/images/" + time1 + "_" + imgfile.getOriginalFilename();
        postVo post = new postVo();
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        String fileUrl = "C:\\images/" + time1 + "_" + imgfile.getOriginalFilename();
        File dest = new File(fileUrl);
        imgfile.transferTo(dest);

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (postService.uploadPost(post) != 0) {
            result.status = true;
            result.message = "success";
            response = new ResponseEntity(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            response = new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @ApiOperation(value = "문화재 게시물 수정")
    @PutMapping(value = "/{postno}")
    public ResponseEntity<?> updatePost(@PathVariable("postno") int postno,
                                        @RequestPart("image") MultipartFile imgfile,
                                        @RequestParam("culturename") String culturename,
                                        @RequestParam("content") String content) throws Exception {

        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
        String filename = "http://localhost:8080/dictionary/images/" + time1 + "_" + imgfile.getOriginalFilename();
        postVo post = new postVo();
        post.setPostno(postno);
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        String fileUrl = "C:\\images/" + time1 + "_" + imgfile.getOriginalFilename();
        File dest = new File(fileUrl);
        imgfile.transferTo(dest);

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (postService.updatePost(post) != 0) {
            result.status = true;
            result.message = "success";
            response = new ResponseEntity(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            response = new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @ApiOperation("문화재 게시물 삭제")
    @DeleteMapping(value="/{postno}")
    public ResponseEntity<?> deletePost(@PathVariable("postno") int postno) throws Exception {

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (postService.deletePost(postno) != 0) {
            result.status = true;
            result.message = "success";
            response = new ResponseEntity(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            response = new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
