package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Board;
import com.example.entity.BoardProjection;
import com.example.entity.Image3;
import com.example.entity.SellerBoard;
import com.example.entity.ShopBoard;
import com.example.repository.BoardRepository;
import com.example.repository.ImageRepository3;
import com.example.repository.SellerBoardRepository;
import com.example.repository.ShopBoardRepository;
import com.example.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import oracle.jdbc.proxy.annotation.Post;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/boards")
public class BoardController {
    @Autowired
    BoardService bService;

    @Autowired
    BoardRepository bRepository;

    @Autowired
    ImageRepository3 i3Repository;

    @Autowired
    SellerBoardRepository sellerBoardRepository;

    @Autowired
    ShopBoardRepository shopBoardRepository;

    // 공지사항 ---------------------------------------------------------------------------------------------------
    // 127.0.0.1:8080/HOST/boards/insert.json
    @PostMapping(value = "/insert.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object postMethodName(@RequestBody Board board) {
        Map<String, Object> map = new HashMap<>();
        try {
            long no = bService.insertBoard(board);
            map.put("status", 200);
            map.put("no", no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/select.json?page=${this.page}&title=?&content=?&writer=
    @GetMapping(value = "/select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectBoardGet(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "title", defaultValue = "") String title,
    @RequestParam(value = "content", defaultValue = "") String content,
    @RequestParam(value = "writer", defaultValue = "") String writer) {
        // 페이지네이션 처리
        PageRequest pageable = PageRequest.of(page - 1, 10);
        
        // if(!title.equals("") && content.equals("") && writer.equals("") ){
        List<BoardProjection> list = bRepository.findByTitleIgnoreCaseContainingOrderByNoDesc(title, pageable);
        // }
        // else 
        List<BoardProjection> list1 = bRepository.findByContentIgnoreCaseContainingOrderByNoDesc(content, pageable);
        List<BoardProjection> list2 = bRepository.findByWriterIgnoreCaseContainingOrderByNoDesc(writer, pageable);
        long pages = bRepository.countByTitleContaining(title);
        
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("contentlist", list1);
            map.put("writerlist", list2);
            map.put("bpage", (pages - 1) / 10 + 1);
            map.put("list", list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/insertboard_image.json
    @PostMapping(value = "/insertboard_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPDImgagePost(@RequestParam(name = "boardno") long boardno,
            @RequestParam(name = "file", required = false) MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            //System.out.println(files.length);
            Board board = bRepository.getById(boardno);
            
            if(files != null){
                List<Image3> list = new ArrayList<>();
                    Image3 image3 = new Image3();
                    image3.setBoard(board);
                    image3.setImage(files.getBytes());
                    image3.setImagename(files.getOriginalFilename());
                    image3.setImagesize(files.getSize());
                    image3.setImagetype(files.getContentType());
                    list.add(image3);
                i3Repository.saveAll(list);
            }
            else{
                Image3 image3 = new Image3();
                image3.setBoard(board);
                image3.setImage(null);
                image3.setImagename("");
                image3.setImagesize(0L);
                image3.setImagetype("");
                i3Repository.save(image3);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/selectboard_image?no=번호
    // <img :src=`/board/selectboard_image?no=${no}` / >
    @RequestMapping(value = "/selectboard_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image3 image3 = i3Repository.findByBoard_no(no);
            if (image3.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image3.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image3.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (image3.getImagetype().equals("image/gif")) {
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(image3.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            // InputStream is =
            // resourceLoader.getResource("classpath:/static/images/default.jpg").getInputStream();

            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
            // headers, HttpStatus.OK);
            return null;
        }
    }

    // 127.0.0.1:8080/HOST/boards/selectoneboard.json?no=1
    @GetMapping(value="/selectoneboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SelectOneBoard(@RequestParam(name = "no") long no) throws IOException{
                Map<String, Object> map = new HashMap<>();
                System.out.println(no);
        try{
            map.put("list", bService.selectoneBoard(no));
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/updateboard_hit.json?no=?
    @PutMapping(value="/updateboard_hit.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object UpdateBoardHit(@RequestParam(name = "no") long no) throws IOException{
            Map<String, Object> map = new HashMap<>();
        try{
            Board board = bRepository.findByNo(no);
            board.setHit(board.getHit()+1);
            bService.updatehit(board);
            map.put("list", board);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/insertboard_image.json
    @PostMapping(value = "/updateboardimage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateBoardImage(@RequestParam(name = "boardno") long boardno,
            @RequestParam(name = "noticeupdatefile", required = false) MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            if(files != null){
                Image3 image3 = i3Repository.findByBoard_no(boardno);
                image3.setImage(files.getBytes());
                image3.setImagename(files.getOriginalFilename());
                image3.setImagesize(files.getSize());
                image3.setImagetype(files.getContentType());
                i3Repository.save(image3);
            }
            map.put("status", 200);
        } catch (Exception e) {
                e.printStackTrace();
                map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/updateboard.json?no=?
    @PutMapping(value="/updateboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateBoard(
        @RequestBody Board board) throws IOException{
        Map<String, Object> map = new HashMap<>();
        try{
            Board no = bRepository.findById(board.getNo()).orElse(null);
            if(board.getTitle().equals("")){
                board.setTitle(no.getTitle());
            }
            if(board.getContent().equals("")){
                board.setContent(no.getContent());
            }
            if(board.getWriter().equals("")){
                board.setWriter(no.getWriter());
            }

            bService.updateBoard(board);
            
            map.put("list", board);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status",e.hashCode());
        }
        return map;
    }

    // delete
    // 127.0.0.1:8080/HOST/boards/deleteboard.json?boardno=
    @DeleteMapping(value = "/deleteboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> DeleteBoard(@RequestParam(name = "boardno") long boardno) throws IOException{
        Map<String, Object> map = new HashMap<>();
        try{
            bService.deleteBoard(boardno);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }


    // 판매QNA -----------------------------------------------------------------------------------------------------------
    //127.0.0.1:8080/HOST/boards/insertsellerboard.json
    @PostMapping(value="/insertsellerboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object InsertSellerBoard(@RequestBody SellerBoard sellerBoard) {
            Map<String, Object> map = new HashMap<>();
            try{
                long sellerboardno = bService.insertSellerBoard(sellerBoard);
                map.put("status", 200);
                map.put("sellerboardno", sellerboardno);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
            return map;
    }

    //127.0.0.1:8080/HOST/boards/selectsellerboard.json?page=1&sellertitle=&sellercontent=&sellerwriter=
    @GetMapping(value="/selectsellerboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectSellerBoard(
        @RequestParam(value = "page" ,defaultValue = "0") int page,
        @RequestParam(value = "sellertitle", defaultValue = "") String sellertitle,
        @RequestParam(value = "sellercontent", defaultValue = "") String sellercontent,
        @RequestParam(value = "sellerwriter", defaultValue = "") String sellerwriter
    ){
        PageRequest pageable = PageRequest.of(page - 1, 10);

        List<SellerBoard> titlelist = sellerBoardRepository.findBySellertitleIgnoreCaseContainingOrderBySellerboardnoDesc(sellertitle, pageable);
        List<SellerBoard> contentlist = sellerBoardRepository.findBySellercontentIgnoreCaseContainingOrderBySellerboardnoDesc(sellercontent, pageable);
        List<SellerBoard> writerlist = sellerBoardRepository.findBySellerwriterIgnoreCaseContainingOrderBySellerboardnoDesc(sellerwriter, pageable);

        long pages = sellerBoardRepository.countBySellertitleContaining(sellertitle);

        Map<String, Object> map = new HashMap<>();
        try{
            map.put("titlelist", titlelist);
            map.put("contentlist", contentlist);
            map.put("writerlist", writerlist);
            map.put("sbpage", (pages - 1) / 10 + 1);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
        
    }

    // 127.0.0.1:8080/HOST/boards/insertsellerboard_image.json
    @PostMapping(value = "/insertsellerboard_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertSellerboard_Image(@RequestParam(name = "sellerboardno") long sellerboardno,
            @RequestParam(name = "files", required = false) MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            SellerBoard sellerBoard= sellerBoardRepository.getById(sellerboardno);
            
            if(files != null){
                List<Image3> list = new ArrayList<>();
                    Image3 image3 = new Image3();
                    image3.setSellerBoard(sellerBoard);
                    image3.setImage(files.getBytes());
                    image3.setImagename(files.getOriginalFilename());
                    image3.setImagesize(files.getSize());
                    image3.setImagetype(files.getContentType());
                i3Repository.saveAll(list);
            }
            else{
                Image3 image3 = new Image3();
                image3.setSellerBoard(sellerBoard);
                image3.setImage(null);
                image3.setImagename("");
                image3.setImagesize(0L);
                image3.setImagetype("");
                i3Repository.save(image3);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

        // 127.0.0.1:8080/HOST/boards/selectsellerboard_image?no=번호
    // <img :src=`/board/selectsellerboard_image?no=${sellerboardno}` / >
    @RequestMapping(value = "/selectsellerboard_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> SelectsellerBoardImage(@RequestParam("no") long sellerboardno) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image3 image3 = i3Repository.findBySellerBoard_sellerboardno(sellerboardno);
            if (image3.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image3.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image3.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (image3.getImagetype().equals("image/gif")) {
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(image3.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            // InputStream is =
            // resourceLoader.getResource("classpath:/static/images/default.jpg").getInputStream();

            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
            // headers, HttpStatus.OK);
            return null;
        }
    }
    
    
    // 127.0.0.1:8080/HOST/boards/selectoneboard.json?sellerboardno=1
    @GetMapping(value="/selectonesellerboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SelectOneSellerBoard(@RequestParam(name = "sellerboardno") long sellerboardno) throws IOException{
                Map<String, Object> map = new HashMap<>();
                System.out.println(sellerboardno);
        try{
            map.put("list", bService.selectOneSellerBoard(sellerboardno));
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/deleteonesellerboard.json?sellerboardno=
    @DeleteMapping(value = "/deleteonesellerboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object DeleteOneSellerBoard(@RequestParam(name = "sellerboardno") long sellerboardno) throws IOException{
            Map<String,Object> map = new HashMap<>();
        try{
            bService.deleteSellerBoard(sellerboardno);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // update
    // 127.0.0.1:8080/HOST/boards/updatesellerboard.json
    @PutMapping(value = "/updatesellerboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public Map<String, Object> UpdateSellerBoard(@RequestBody SellerBoard sellerBoard) throws IOException{
        Map<String, Object> map = new HashMap<>();
            try{
                SellerBoard no = sellerBoardRepository.findById(sellerBoard.getSellerboardno()).orElse(null);
                if(sellerBoard.getSellertitle().equals("")){
                    sellerBoard.setSellertitle(no.getSellertitle());
                }
                if(sellerBoard.getSellercontent().equals("")){
                    sellerBoard.setSellercontent(no.getSellercontent());
                }
                if(sellerBoard.getSellerwriter().equals("")){
                    sellerBoard.setSellerwriter(no.getSellerwriter());
                }

                bService.updateSellerBoard(sellerBoard);

                map.put("list", sellerBoard);
                map.put("status", 200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
        return map;
    }

    // update image
    // 127.0.0.1:8080/HOST/boards/updatesellerboardimage.json
    @PostMapping(value = "/updatesellerboardimage.json",consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateSellerBoardImage(
        @RequestParam(name = "sellerboardno") long sellerboardno,
        @RequestParam(name = "sellerboardupdatefile", required = false) MultipartFile files) throws IOException{
            Map<String, Object> map = new HashMap<>();
            try{
                if(files != null){
                    Image3 image3 = i3Repository.findBySellerBoard_sellerboardno(sellerboardno);
                    image3.setImage(files.getBytes());
                    image3.setImagename(files.getOriginalFilename());
                    image3.setImagesize(files.getSize());
                    image3.setImagetype(files.getContentType());
                    i3Repository.save(image3);
                }
                map.put("status", 200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
            return map;
        }
    

    // update hit
    // 127.0.0.1:8080/HOST/boards/updatesellerboardhit.json?sellerboardno=
    @PutMapping(value = "/updatesellerboardhit.json",consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateSellerBoardHit(@RequestParam(name = "sellerboardno") long sellerboardno) throws IOException{
        Map<String, Object> map = new HashMap<>();
            try{
                SellerBoard sellerBoard = sellerBoardRepository.findBySellerboardno(sellerboardno);
                sellerBoard.setSellerhit(sellerBoard.getSellerhit()+1);
                bService.updatesellerboardhit(sellerBoard);
                map.put("list", sellerBoard);
                map.put("status", 200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put(("status"), e.hashCode());
            }
        return map;
    }


    // 쇼핑QNA -----------------------------------------------------------------------------------------------------------

    //  127.0.0.1:8080/HOST/boards/insertshopboard.json
    @PostMapping(value="/insertshopboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object InsertShopBoard(@RequestBody ShopBoard shopBoard) {
        Map<String, Object> map = new HashMap<>();
        try{
            long shopboardno = bService.insertShopBoard(shopBoard);
            map.put("status", 200);
            map.put("shopboardno", shopboardno);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/insertShopboard_image.json
    @PostMapping(value = "/insertShopboard_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertShopboard_Image(@RequestParam(name = "shopboardno") long shopboardno,
            @RequestParam(name = "shopfile") MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            ShopBoard shopBoard= shopBoardRepository.getById(shopboardno);
            if(files != null){
            List<Image3> list = new ArrayList<>();
                Image3 image3 = new Image3();
                image3.setShopBoard(shopBoard);
                image3.setImage(files.getBytes());
                image3.setImagename(files.getOriginalFilename());
                image3.setImagesize(files.getSize());
                image3.setImagetype(files.getContentType());
                list.add(image3);
                i3Repository.saveAll(list);
            }
            else{
                Image3 image3 = new Image3();
                image3.setShopBoard(shopBoard);
                image3.setImage(null);
                image3.setImagename("");
                image3.setImagesize(0L);
                image3.setImagetype("");
                i3Repository.save(image3);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/selectshopboard.json?page=&shoptitle=&shopcontent=&shopwriter=
    @GetMapping(value="/selectshopboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SelectShopBoard(
        @RequestParam(value = "page", defaultValue = "0")int page,
        @RequestParam(value = "shoptitle", defaultValue = "")String shoptitle,
        @RequestParam(value = "shopcontent", defaultValue = "")String shopcontent,
        @RequestParam(value = "shopwriter", defaultValue = "")String shopwriter
    ) {
        Map<String, Object> map = new HashMap<>();

        PageRequest pageable = PageRequest.of(page - 1, 10);

        List<ShopBoard> shoptitlelist = shopBoardRepository.findByShoptitleIgnoreCaseContainingOrderByShopboardnoDesc(shoptitle, pageable);
        List<ShopBoard> shopcontentlist = shopBoardRepository.findByShopcontentIgnoreCaseContainingOrderByShopboardnoDesc(shopcontent, pageable);
        List<ShopBoard> shopwriterlist = shopBoardRepository.findByShopwriterIgnoreCaseContainingOrderByShopboardnoDesc(shopwriter, pageable);

        long pages = shopBoardRepository.countByShoptitleContaining(shoptitle);
        try{
            map.put("shoptitlelist", shoptitlelist);
            map.put("shopcontentlist", shopcontentlist);
            map.put("shopwriterlist", shopwriterlist);
            map.put("shopboardpage", (pages - 1) / 10 + 1);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/boards/selectshopboard_image?no=번호
    // <img :src=`HOST/board/selectshopboard_image?no=${shopboardno}` / >
    @RequestMapping(value = "/selectshopboard_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> SelectshopBoardImage(@RequestParam("no") long shopboardno) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(shopboardno);
            Image3 image3 = i3Repository.findByShopBoard_shopboardno(shopboardno);
            System.out.println(image3.getImagename());
            if (image3.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image3.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image3.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (image3.getImagetype().equals("image/gif")) {
                }

                // 클래스명 response = new 클래스명( 생성자선택 )
                ResponseEntity<byte[]> response = new ResponseEntity<>(image3.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            // InputStream is =
            // resourceLoader.getResource("classpath:/static/images/default.jpg").getInputStream();

            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
            // headers, HttpStatus.OK);
            return null;
        }
    }
    
    // 127.0.0.1:8080/HOST/boards/selectoneshopboard.json?shopboardno=1
    @GetMapping(value="/selectoneshopboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SelectOneShopBoard(@RequestParam(name = "shopboardno") long shopboardno) throws IOException{
                Map<String, Object> map = new HashMap<>();
                System.out.println(shopboardno);
        try{
            map.put("list", bService.selectOneShopBoard(shopboardno));
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // update
    // 127.0.0.1:8080/HOST/boards/updateshopboard.json
    @PutMapping(value = "/updateshopboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateShopBoard(
        @RequestBody ShopBoard shopBoard
    )   throws IOException{
        Map<String, Object> map = new HashMap<>();
            try{
                ShopBoard no = shopBoardRepository.findByShopboardno(shopBoard.getShopboardno());
                if(shopBoard.getShoptitle().equals("")){
                    shopBoard.setShoptitle(no.getShoptitle());
                }
                if(shopBoard.getShopcontent().equals("")){
                    shopBoard.setShopcontent(no.getShopcontent());
                }
                if(shopBoard.getShopwriter().equals("")){
                    shopBoard.setShopwriter(no.getShopwriter());
                }
                bService.updateShopBoard(shopBoard);

                map.put("list", shopBoard);
                map.put("status", 200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
        return map;
    }

    // update image
    // 127.0.0.1:8080/HOST/boards/updateshopboardimage.json
    @PostMapping(value = "/updateshopboardimage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateShopBoardImage(@RequestParam(name = "shopboardno") long shopboardno,
            @RequestParam(name = "shopboardupdatefile", required = false) MultipartFile files) throws IOException {
        // System.out.println(files.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        try {
            if(files != null){
                Image3 image3 = i3Repository.findByShopBoard_shopboardno(shopboardno);
                image3.setImage(files.getBytes());
                image3.setImagename(files.getOriginalFilename());
                image3.setImagesize(files.getSize());
                image3.setImagetype(files.getContentType());
                i3Repository.save(image3);
            }
            map.put("status", 200);
        } catch (Exception e) {
                e.printStackTrace();
                map.put("status", e.hashCode());
        }
        return map;
    }

    // update hit
    // 127.0.0.1:8080/HOST/boards/updateShopboard_hit.json?no=?
    @PutMapping(value="/updateShopboard_hit.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object UpdateShopBoardHit(@RequestParam(name = "shopboardno") long shopboardno) throws IOException{
            Map<String, Object> map = new HashMap<>();
        try{
            ShopBoard shopBoard = shopBoardRepository.findByShopboardno(shopboardno);
            shopBoard.setShophit(shopBoard.getShophit()+1);
            bService.updateshopboardhit(shopBoard);

            map.put("list", shopBoard);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();

        }
        return map;
    }
    // delete
    @DeleteMapping(value = "/deleteshopboard.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> DeleteShopBoard(@RequestParam(name = "shopboardno") long shopboardno) throws IOException{
        Map<String, Object> map = new HashMap<>();
        try{
            bService.deleteShopBoard(shopboardno);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 유저정보 -----------------------------------------------------------------------------------------------------------
    // 127.0.0.1:8080/HOST/boards/selectuserlist.json
    @GetMapping(value="/selectuserlist.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SelectUserList() {
        Map<String, Object> map = new HashMap<>();
        try{
            map.put("list", bService.selectCompanys());
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    
    
    
}
