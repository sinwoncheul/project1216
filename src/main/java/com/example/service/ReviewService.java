package com.example.service;

import java.util.List;

import com.example.entity.ProductReview;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    public int insertReview(ProductReview productReview);

    List<ProductReview> selectReview(Long productno);

}
