package com.example.repository;

import java.util.List;

import com.example.entity.WishList;

import org.apache.ibatis.annotations.Param;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishList, Long> {

    WishList findByCompany_companyidAndProduct_productnoAndInsertcompany_insertcompanyno(String companyid,
            Long productno, Long insertcompanyno);

    WishList findByCompany_companyidAndProduct_productno(String companyid,
            Long productno);

    @Query(value = "SELECT SUM(WISH_COUNT) FROM WISH WHERE COMPANY_ID=:id", nativeQuery = true)
    long queryCountByWishList(@Param("id") String id);

}
