package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Company;
import com.example.entity.Image2;
import com.example.entity.Insertcompany;
import com.example.entity.Product;
import com.example.entity.WishList;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.repository.CompanyRepository2;
import com.example.repository.ImageRepository2;
import com.example.repository.ProductRepository;
import com.example.repository.WishRepository;
import com.example.service.CompanyService2;
import com.example.service.WishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/wish")
public class WishController {

    @Autowired
    CompanyRepository2 cRepository2;

    @Autowired
    JwtUtil jUtil;

    @Autowired
    CompanyRepository cRepository;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    WishRepository wRepository;

    @Autowired
    WishService wishService;

    @Autowired
    ImageRepository2 imageRepository2;

    @Autowired
    CompanyRepository2 companyRepository2;

    @Autowired
    CompanyService2 companyService2;

    // 기업별 토탈 위시리스트
    // 127.0.0.1:8080/HOST/wish/company2_total_hit.json
    @GetMapping(value = "/company2_total_hit.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> company2_total_hit() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("hitlist", wishService.companycarthit());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 로그인 시 해당 위시리스트 잔여 갯수
    // 127.0.0.1:8080/HOST/wish/update_wish_hit_select.json
    @GetMapping(value = "/update_wish_hit_select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatewishhit(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jUtil.extractUsername(token);
            map.put("wishcount", wRepository.queryCountByWishList(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 위시리스트 조회수 (기업 좋아요)
    // 127.0.0.1:8080/HOST/wish/wish_hit_Put.json?no=
    @PutMapping(value = "/wish_hit_Put.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> wish_hit(@RequestParam("no") long no, @RequestBody Product product,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰을 이용해서 아이디 추출
            String userid = jUtil.extractUsername(token);
            // 회원정보 꺼내기
            Company companyid = cRepository.getById(userid);
            // 상품정보 코드 꺼내기
            Product productid = pRepository.getById(no);
            // 기업정보 코드 꺼내기
            // Insertcompany insertcompanyid =
            // companyRepository2.getById(insertcompany.getInsertcompanyno());
            // 조회
            // WishList wList =
            // wRepository.findByCompany_companyidAndProduct_productno(userid,
            // productid.getProductno());
            WishList wList = wRepository.findByCompany_companyidAndProduct_productno(userid, productid.getProductno());
            if (wList == null) {
                wList = new WishList();
                wList.setCompany(companyid);
                wList.setProduct(productid);
                // wList.setInsertcompany(insertcompanyid);
                wList.setWishcount(1L);
                wRepository.save(wList);
            } else {
                if (wList.getWishcount() == 0) {
                    wList.setWishcount(1L);
                } else if (wList.getWishcount() == 1) {
                    wList.setWishcount(0L);
                }
                wRepository.save(wList);
            }
            // map.put("wishcount", wRepository.queryCountByWishList(userid));
            // map.put("desclist", cRepository2.findAllByOrderByInsertcompanynoDesc());
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 위시리스트 조회
    // 127.0.0.1:8080/HOST/wish/selectwish.json
    @GetMapping(value = "/selectwish.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectwishListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰을 이용해서 아이디 추출
            String id = jUtil.extractUsername(token);
            map.put("wishlist", wishService.wishListselect(id));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/wish/wishselect_image?no=번호
    @RequestMapping(value = "/wishselect_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage1(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image2 image2 = imageRepository2.findByProduct_productno(no);
            System.out.println(image2);
            if (image2.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (image2.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (image2.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(image2.getImage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 127.0.0.1:8080/HOST/wish/wish_delete?no
    @DeleteMapping(value = "/wish_delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteWishDelete(@RequestParam("no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            wishService.wishListdelete(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
