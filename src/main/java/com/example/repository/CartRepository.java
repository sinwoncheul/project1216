package com.example.repository;

import com.example.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByCompany_companyidAndProduct_productno(String companyid, Long productno);

    Cart findByCompany_companyid(String companyid);

    Cart findByCartcount(long cartcount);

    @Query(value = "SELECT SUM(WISH_COUNT) FROM WISH WHERE COMPANY_ID=:id", nativeQuery = true)
    long queryCountByCartList(@Param("id") String id);

    @Query(value = "SELECT SUM(CART_COUNT) FROM CART WHERE COMPANY_ID=:id", nativeQuery = true)
    String queryCountByCart(@Param("id") String id);

    // 카트 카운트가 1인 것만 조회
    @Query(value = "SELECT * FROM CART WHERE CART_COUNT=1 AND COMPANY_ID=:id", nativeQuery = true)
    String queryCountByCart_count(@Param("id") String id);

}
