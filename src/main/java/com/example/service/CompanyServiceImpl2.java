package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Insertcompany;
import com.example.repository.CompanyRepository2;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl2 implements CompanyService2 {

    @Autowired
    CompanyRepository2 cRepository2;

    @Override
    public long insertCompany(Insertcompany insertcompany) {
        try {
            Insertcompany ret = cRepository2.save(insertcompany);
            return ret.getInsertcompanyno();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Insertcompany> selectCompanyList() {
        return cRepository2.findAll();
    }

    @Override
    public int deleteCompany(long insertcompanyno) {
        cRepository2.deleteById(insertcompanyno);
        return 0;
    }

    @Override
    public int updateCompany(Insertcompany insertcompany) {
        cRepository2.save(insertcompany);
        return 0;
    }

    @Override
    public Insertcompany selectCompany(long no) {
        Optional<Insertcompany> insertcompany = cRepository2.findById(no);
        return insertcompany.orElse(null);
    }

    @Override
    public List<Insertcompany> selectinsertcompany(String companyid) {

        return cRepository2.findAllByCompany_companyid(companyid);
    }

}
