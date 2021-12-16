package com.example.repository;

import java.util.List;

import com.example.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCompany_companyid(String id);

    List<Product> findByCompany_companyid(String id);


    List<Product> findAllByInsertcompany_insertcompanyno(long no);

    Product findByProductno(Long productno);

    List<Product> findByProducttitleIgnoreCaseContainingOrProductcontentIgnoreCaseContainingOrderByProductnoDesc(String producttitle,String productcontent, Pageable pageable);


    long countByProductcontentOrProducttitleContaining(String productcontent, String producttitle);

}
