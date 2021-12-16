package com.example.repository;

import com.example.entity.ProductDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDtailRepository extends JpaRepository<ProductDetail, Long> {

}
