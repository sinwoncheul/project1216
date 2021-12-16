package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Company;
import com.example.entity.Product;
import com.example.entity.ProductReview;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ReviewRepository;
import com.example.service.ProductService;
import com.example.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    @Autowired
    ReviewRepository repository;

    @Autowired
    ReviewService rService;

    @Autowired
    ProductService pService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CompanyRepository cRepository;

    @GetMapping(value = "/test")
    public Object ooore() {
        return "tetstst";
    }

    // 127.0.0.1:8080/HOST/review/insert.json
    @PostMapping(value = "/insert.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertReview(@RequestParam("productno") long productno, @RequestBody ProductReview productReview,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        System.out.println(token);
        try {
            String userid = jwtUtil.extractUsername(token);
            Company comp = cRepository.findById(userid).orElse(null);
            Product product = pService.selectItemAlone(productno);
            productReview.setCompany(comp);
            productReview.setProduct(product);

            int no = rService.insertReview(productReview);
            map.put("status", 200);
            map.put("no", no);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/review/selectreview.json?page=1&writercontent&productno=
    @GetMapping(value = "/selectreview.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectone1(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "writercontent", defaultValue = "") String writercontent,
            @RequestParam("productno") long productno) {
        // System.out.println(writercontent.toString());
        Map<String, Object> map = new HashMap<>();

        try {
            PageRequest pageable = PageRequest.of(page - 1, 3);
            List<ProductReview> pList = repository.findAllByOrderByReviewnoDesc(pageable);
            List<ProductReview> liv = rService.selectReview(productno);

            map.put("list", pList);
            map.put("liv", liv);
            map.put("status", 200);

            long pages = repository.countByWritercontentContaining(writercontent);
            System.out.println(pages);
            map.put("pages", pages);
            map.put("productpages", (pages - 1) / 3 + 1);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
