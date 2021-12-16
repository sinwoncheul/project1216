package com.example.repository;

import java.util.List;

import com.example.entity.SellerBoard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerBoardRepository extends JpaRepository<SellerBoard, Long>{
    
    // SellerBoard findByNBoard(Long no);
    SellerBoard findBySellerboardno(long sellerboardno);

    long countBySellertitleContaining(String sellertitle);

    List<SellerBoard> findBySellertitleIgnoreCaseContainingOrderBySellerboardnoDesc(String sellertitle, Pageable pageable);
    List<SellerBoard> findBySellercontentIgnoreCaseContainingOrderBySellerboardnoDesc(String sellercontent, Pageable pageable);
    List<SellerBoard> findBySellerwriterIgnoreCaseContainingOrderBySellerboardnoDesc(String sellerwriter, Pageable pageable);
}
