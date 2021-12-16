package com.example.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.entity.InsertCompanyProjection;
import com.example.entity.Insertcompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository2 extends JpaRepository<Insertcompany, Long> {
    Insertcompany findByCompany_companyid(String id); //

    List<Insertcompany> findAllByCompany_companyid(String id); //

    Insertcompany findByInsertcompanyno(Long no);

    Insertcompany findAllByInsertcompanyno(long no);

    List<Insertcompany> findAllByOrderByInsertcompanynoDesc();

    // SELECT * FROM INSERTCOMPANY ORDER BY INSERTCOMPANY_DATE DESC

    @Query(value = "SELECT * FROM INSERTCOMPANY", nativeQuery = true)
    String queryCountByCart_count();

}
