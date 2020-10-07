package com.web.dictionary.controller;

import com.web.dictionary.dto.Culture;
import com.web.dictionary.model.BasicResponse;
import com.web.dictionary.service.ICultureService;
import com.web.dictionary.service.JwtService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Map m = jwtService.getKey("userno");
        int userno = 0;
        if (m.containsKey("userno")) {
            userno = (int) m.get("userno");
        }
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
        logger.info("culturename: " + culturename);
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
    @GetMapping(value = "/search")
    public ResponseEntity<?> searchCultureInfoByCultureName(@RequestParam("culturename") String culturename) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        List<Culture> list = cultureService.searchCultureInfoByCultureName(culturename);
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
        Map m = jwtService.getKey("userno");
        if(m.containsKey("userno")) {
            int userno = (int) m.get("userno");
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
        } else {
            List<Culture> list = new ArrayList<>();
            result.status = true;
            result.message = "success";
            result.object = list;
            return response = new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "찜 등록")
    @PostMapping(value = "/regfavorite")
    public ResponseEntity<?> registFavoriteCulture(@RequestBody Culture post) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();
        Map m = jwtService.getKey("userno");
        int userno = (int) m.get("userno");
        logger.info("" + userno);
        if (cultureService.registFavoriteCulture(post.getPostno(), userno)) {
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
        Map m = jwtService.getKey("userno");
        int userno = (int) m.get("userno");
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
                                        @RequestParam("culturename") String culturename, @RequestParam("content") String content,
                                        @RequestParam("ename") String ename, @RequestParam("category") String category,
                                        @RequestParam("generation") String generation, @RequestParam("constructionperiod") String constructionperiod,
                                        @RequestParam("location") String location, @RequestParam("lng") String lng,
                                        @RequestParam("lat") String lat) throws Exception {
        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
//        String filename = "http://localhost:8080/dictionary/images/" + time1 + "_" + imgfile.getOriginalFilename();
        String filename = "http://j3b202.p.ssafy.io:8088/dictionary/images/cultures/" + time1 + "_" + imgfile.getOriginalFilename();
        Culture post = new Culture();
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        post.setGeneration(generation);
        post.setEname(ename);
        post.setCategory(category);
        post.setConstructionperiod(constructionperiod);
        post.setLocation(location);
        post.setLng(lng);
        post.setLat(lat);
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
                                        @RequestParam("culturename") String culturename, @RequestParam("content") String content,
                                        @RequestParam("ename") String ename, @RequestParam("category") String category,
                                        @RequestParam("generation") String generation, @RequestParam("constructionperiod") String constructionperiod,
                                        @RequestParam("location") String location, @RequestParam("lng") String lng,
                                        @RequestParam("lat") String lat) throws Exception {

        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
        String filename = "http://j3b202.p.ssafy.io:8088/dictionary/images/cultures/" + time1 + "_" + imgfile.getOriginalFilename();

        Culture post = new Culture();
        post.setPostno(postno);
        post.setCulturename(culturename);
        post.setContent(content);
        post.setImageUrl(filename);
        post.setGeneration(generation);
        post.setEname(ename);
        post.setCategory(category);
        post.setConstructionperiod(constructionperiod);
        post.setLocation(location);
        post.setLng(lng);
        post.setLat(lat);
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
    @PostMapping(value = "/image")
    public ResponseEntity<?> getCultureByImage(@RequestPart("image") MultipartFile file) throws Exception {
        ResponseEntity response = null;
        BasicResponse result = new BasicResponse();

        //image파일에 저장된 데이터 삭제
        String path = "/home/ubuntu/model/image";
        removeDirectory(path);

        //저장
        saveImage(path, file);
        System.out.println("Python Call");
        String[] command = new String[4];
        command[0] = "python";
        command[1] = "/home/ubuntu/model/test_frcnn.py";
        command[2] = "--path";
        command[3] = "/home/ubuntu/model/image";
        //python test_frcnn.py --path image

        try {
            ByteArrayOutputStream out =  execPython(command);
            String extact_result = out.toString().split("result:")[1];
            StringBuffer res = new StringBuffer();
            for (int i = 0; i < extact_result.length(); i++) {
                char c = extact_result.charAt(i);
                if(c =='\n' || c== '\r'){
                    break;
                }else if(c != ' ') {
                    res.append(c);
                }
            }
            if(!res.toString().trim().equals("")) {
                //정보 가져오기
                result.object = cultureService.getCultureInfoByEName(res.toString());

                response = new ResponseEntity(result,HttpStatus.OK);
            }else {
                response = new ResponseEntity(result,HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void saveImage(String path, MultipartFile file) throws IllegalStateException, IOException {
        SimpleDateFormat format1 = new SimpleDateFormat("yyMMDDHHmmss");
        String time1 = format1.format(new Date());
        String fileUrl = path+"/"+time1 + "_" + file.getOriginalFilename();
        File dest = new File(fileUrl);
        file.transferTo(dest);
    }

    private void removeDirectory(String path) {
        File deleteFolder = new File(path);
        File[] deleteFolderList = deleteFolder.listFiles();
        for (int j = 0; j < deleteFolderList.length; j++ ) {
            deleteFolderList[j].delete();
        }
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
