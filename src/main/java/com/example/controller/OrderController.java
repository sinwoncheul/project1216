package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Company;
import com.example.entity.Option;
import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.repository.CompanyRepository;
import com.example.repository.OptionRepository;
import com.example.repository.OrderProductRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    // 아이디

    // 상품

    @Autowired
    CompanyRepository cRepository;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    OrderRepository oRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    OptionRepository optionRepository;

    // @Autowired
    // OrderProductRepository orderProductRepository;

    // 127.0.0.1:8080/HOST/order/insertorder.json?productno=&optionno=
    @PostMapping(value = "/insertorder.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postMethodName(
            @RequestBody Order order,
            @RequestHeader("token") String token,
            @RequestParam(name = "productno") long productno,
            @RequestParam(name = "optionno") long optionno) {

        System.out.println(order.toString());
        // System.out.println(token.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            String id = jwtUtil.extractUsername(token);

            long no = pRepository.findByProductno(productno).getProductno();

            Product product = pRepository.findById(no).get();
            Company company = cRepository.findById(id).get();
            Option option = optionRepository.findByOptionno(optionno);

            order.setCompany(company);
            order.setProduct(product);
            order.setOption(option);

            oRepository.save(order);

            map.put("status", 200);
            map.put("order", order);
            map.put("option", option);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }

        return map;
    }

    // 127.0.0.1:8080/HOST/order/selectorder.json
    @GetMapping(value = "/selectorder.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMethodName(@RequestParam("orderno") long orderno) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Order> order = oRepository.findAllByOrderByOrdernoDesc();
            map.put("list", order);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
