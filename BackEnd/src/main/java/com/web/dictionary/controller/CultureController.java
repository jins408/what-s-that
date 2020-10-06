package com.web.dictionary.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.dictionary.dto.Culture;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.ICultureService;
import com.web.dictionary.service.JwtService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/culture")
@CrossOrigin(origins = {"*"})
@RestController
public class CultureController {
    private static final Logger logger = LoggerFactory.getLogger(CultureController.class);

    @Autowired
    ICultureService cultureService;
    @Autowired
    JwtService jwtService;

    @ApiOperation(value = "문화재 상세페이지 조회 (문화재이름, 문화재내용, 찜 상태(1이면 찜한 상태)")
    @GetMapping(value = "/detail/{postno}")
    public ResponseEntity<?> getDetailCulturePost(@PathVariable("postno") int postno) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        String strKey = (String) jwtService.getKey("userno");
        int userno = 0;
        if (!strKey.equals("none")) userno = Integer.parseInt(strKey);
        Culture culture = cultureService.getDetailCulturePost(postno, userno);
        if (culture != null) {
            result.status = true;
            result.message = "success";
            result.object = culture;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "문화재 이름으로 문화재 조회")
    @GetMapping(value = "/find")
    public ResponseEntity<?> getCultureInfoByCultureName(@RequestParam(value = "culturename", required = false) String culturename) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        List<Culture> culture = cultureService.getCultureInfoByCultureName(culturename);
        if (culture != null) {
            result.status = true;
            result.message = "success";
            result.object = culture;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "문화재 이름으로 문화재 검색 (자동완성)")
    @GetMapping(value = "/search/{culturename}")
    public ResponseEntity<?> searchCultureInfoByCultureName(@PathVariable("culturename") String culturename) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        List<String> list = cultureService.searchCultureInfoByCultureName(culturename);
        if (list != null) {
            result.status = true;
            result.message = "success";
            result.object = list;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "찜한 게시글 불러오기")
    @GetMapping(value = "/favorite")
    public ResponseEntity<?> getfavoritePost() throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        int userno = (int) jwtService.getKey("userno");
        logger.info("" + userno);
        List<Culture> list = cultureService.getfavoritePost(userno);
        if (list != null) {
            result.status = true;
            result.message = "success";
            result.object = list;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "찜 등록")
    @PostMapping(value = "/regfavorite")
    public ResponseEntity<?> registFavoriteCulture(@RequestParam("postno") int postno) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        int userno = (int) jwtService.getKey("userno");
        logger.info("" + userno);
        if (cultureService.registFavoriteCulture(postno, userno)) {
            result.status = true;
            result.message = "success";
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "찜 삭제")
    @DeleteMapping(value = "/favorite/{postno}")
    public ResponseEntity<?> deleteFavoriteCulture(@PathVariable("postno") int postno) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        int userno = (int) jwtService.getKey("userno");
        logger.info("" + userno);
        if (cultureService.deleteFavoriteCulture(postno, userno)) {
            result.status = true;
            result.message = "success";
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = false;
            result.message = "fail";
            return response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "문화재 게시물 생성")
    @PostMapping(value = "/admin")
    public ResponseEntity<?> uploadPost(@RequestPart("image") MultipartFile imgfile,
                                        @RequestParam("culturename") String culturename,
                                        @RequestParam("content") String content) throws Exception {
        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
//        String filename = "http://localhost:8080/dictionary/images/" + time1 + "_" + imgfile.getOriginalFilename();
        String filename = "http://j3b202.p.ssafy.io:8088/dictionary/images/cultures/" + time1 + "_" + imgfile.getOriginalFilename();
        Culture post = new Culture();
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        String fileUrl = "/home/ubuntu/springboot/images/cultures/" + time1 + "_" + imgfile.getOriginalFilename();
        File dest = new File(fileUrl);
        imgfile.transferTo(dest);

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (cultureService.uploadPost(post) != 0) {
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
    @PutMapping(value = "/admin/{postno}")
    public ResponseEntity<?> updatePost(@PathVariable("postno") int postno,
                                        @RequestPart("image") MultipartFile imgfile,
                                        @RequestParam("culturename") String culturename,
                                        @RequestParam("content") String content) throws Exception {

        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
        String filename = "http://j3b202.p.ssafy.io:8088/dictionary/images/culture/" + time1 + "_" + imgfile.getOriginalFilename();

        Culture post = new Culture();
        post.setPostno(postno);
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        String fileUrl = "/home/ubuntu/springboot/images/cultures/" + time1 + "_" + imgfile.getOriginalFilename();

        File dest = new File(fileUrl);
        imgfile.transferTo(dest);

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (cultureService.updatePost(post) != 0) {
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
    @DeleteMapping(value = "/admin/{postno}")
    public ResponseEntity<?> deletePost(@PathVariable("postno") int postno) throws Exception {

        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        if (cultureService.deletePost(postno) != 0) {
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

    
    @ApiOperation(value = "이미지로 검색")
    @GetMapping(value = "/image")
    public ResponseEntity<?> getCultureByImage() throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        System.out.println("Python Call");
        String[] command = new String[4];
        command[0] = "python";
        command[1] = "/home/ubuntu/model/test_frcnn.py";
        command[2] = "--path";
        command[3] = "/home/ubuntu/model/image";
        
        //python test_frcnn.py --path image
        try {
        	ByteArrayOutputStream out =  execPython(command);
        	String res = out.toString();
        	result.object = res.split("result:")[1];
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity(result,HttpStatus.OK);
    }


	public static ByteArrayOutputStream execPython(String[] command) throws IOException, InterruptedException {
	    CommandLine commandLine = CommandLine.parse(command[0]);
	    for (int i = 1, n = command.length; i < n; i++) {
	        commandLine.addArgument(command[i]);
	    }
	
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
	    DefaultExecutor executor = new DefaultExecutor();
	    executor.setStreamHandler(pumpStreamHandler);
	    int result = executor.execute(commandLine);
	    System.out.println("result: " + result);
	    System.out.println("output: " + outputStream.toString());
	    return outputStream;
	}    
}
