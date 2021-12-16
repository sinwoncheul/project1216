package com.example.repository;


import java.util.List;

import com.example.entity.ShopBoard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopBoardRepository extends JpaRepository<ShopBoard, Long>{
    ShopBoard findByShopboardno(long shopboardno);

    List<ShopBoard> findByShoptitleIgnoreCaseContainingOrderByShopboardnoDesc(String shoptitle, Pageable pageable);
    List<ShopBoard> findByShopcontentIgnoreCaseContainingOrderByShopboardnoDesc(String shopcontent, Pageable pageable);
    List<ShopBoard> findByShopwriterIgnoreCaseContainingOrderByShopboardnoDesc(String shopwriter, Pageable pageable); 
    
    long countByShoptitleContaining(String shoptitle);
}
