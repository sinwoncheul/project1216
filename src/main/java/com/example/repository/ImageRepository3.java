package com.example.repository;

import org.springframework.stereotype.Repository;
import com.example.entity.Image3;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImageRepository3 extends JpaRepository<Image3, Long> {
    Image3 findByShopBoard_shopboardno(long shopboardno);

    Image3 findByBoard_no(long no);

    Image3 findBySellerBoard_sellerboardno(long sellerboardno);
}
