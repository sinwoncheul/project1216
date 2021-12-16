package com.example.service;

import java.util.List;

import com.example.entity.ProductReview;
import com.example.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository repository;

    @Override
    public int insertReview(ProductReview productReview) {
        try {
            repository.save(productReview);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ProductReview> selectReview(Long productno) {

        return repository.findByProduct_productno(productno);
    }

}
