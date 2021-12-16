package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.dto.CartDto;
import com.example.entity.Cart;
import com.example.entity.Company;
import com.example.entity.Image2;
import com.example.entity.Product;
import com.example.entity.WishList;
import com.example.jwt.JwtUtil;
import com.example.repository.CartRepository;
import com.example.repository.CompanyRepository;
import com.example.repository.ImageRepository2;
import com.example.repository.ProductRepository;
import com.example.repository.WishRepository;
import com.example.service.CartService;
import com.example.service.WishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    CartService cService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    JwtUtil jUtil;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    WishRepository wRepository;

    @Autowired
    WishService wishService;

    @Autowired
    ImageRepository2 imageRepository2;

    // 회원별 카트 장바구니 갯수
    // 127.0.0.1:8080/HOST/cart/cartSelectCount.json
    @GetMapping(value = "/cartSelectCount.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cartSelectCount(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jUtil.extractUsername(token);
            map.put("CartCount", cartRepository.queryCountByCart(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/cart/insertcart.json?no=1
    @PutMapping(value = "/insertcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertProductPost(@RequestParam("no") long no, @RequestBody Product product,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰 회원 아이디 추출
            String id = jUtil.extractUsername(token);
            // 상품 코드 추출
            // Product product2 = pRepository.getById(product.getProductno());
            Product product2 = pRepository.getById(no);
            // 회원 정보 꺼내기
            Company company = companyRepository.getById(id);
            // 회원과 상품 코드가 있는지 없는지 체크
            Cart cart = cartRepository.findByCompany_companyidAndProduct_productno(id, product2.getProductno());
            // 없을 때
            if (cart == null) {
                cart = new Cart();
                cart.setProduct(product2);
                cart.setCompany(company);
                cart.setCartcount(1L);
                cartRepository.save(cart);
                // 있을 때
            } else {
                if (cart.getCartcount() == 0) {
                    cart.setCartcount(1L);
                } else if (cart.getCartcount() == 1) {
                    cart.setCartcount(0L);
                }
                cartRepository.save(cart);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 장바구니 리스트 조회
    // 127.0.0.1:8080/HOST/cart/selectcart.json
    @GetMapping(value = "/selectcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectcartListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jUtil.extractUsername(token);
            map.put("list", cService.selectCart(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 카트 조회수
    // 127.0.0.1:8080/HOST/wish/update_wish_hit.json
    @PutMapping(value = "/update_wish_hit.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatehit(@RequestHeader("token") String token, @RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 토큰을 이용해서 아이디 추출
            String userid = jUtil.extractUsername(token);

            // 회원정보 꺼내기
            Company company = companyRepository.getById(userid);

            // 상품정보 코드 꺼내기
            Product product2 = pRepository.getById(product.getProductno());

            // 조회
            Cart cart = cartRepository.findByCompany_companyidAndProduct_productno(userid, product2.getProductno());

            if (cart == null) {
                cart = new Cart();
                cart.setCompany(company);
                cart.setProduct(product2);
                cart.setCartcount(1L);
                cartRepository.save(cart);
            } else {
                if (cart.getCartcount() == 0) {
                    cart.setCartcount(1L);
                } else if (cart.getCartcount() == 1) {
                    cart.setCartcount(0L);
                }
                cartRepository.save(cart);
            }
            map.put("cartcount", cartRepository.queryCountByCartList(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/cart/cartselect_image?no=번호
    @RequestMapping(value = "/cartselect_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage1(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            Image2 image2 = imageRepository2.findByProduct_productno(no);
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
            // InputStream is =
            // resourceLoader.getResource("classpath:/static/images/default.jpg").getInputStream();

            // HttpHeaders headers = new HttpHeaders();
            // headers.setContentType(MediaType.IMAGE_JPEG);
            // ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(),
            // headers, HttpStatus.OK);
            return null;
        }
    }

}
