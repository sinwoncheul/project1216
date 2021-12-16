package com.example.repository;

import com.example.entity.Image2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository2 extends JpaRepository<Image2, Long> {
    Image2 findByProduct_productno(Long no);
 
}
