package com.example.repository;

import java.util.List;

import com.example.entity.ProductReview;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ProductReview, Long> {

    List<ProductReview> findByProduct_productno(Long productno);

    List<ProductReview> findAllByOrderByReviewnoDesc(Pageable pageable);

    long countByWritercontentContaining(String writercontent);

}
