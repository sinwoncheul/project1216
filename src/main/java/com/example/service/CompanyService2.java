package com.example.service;

import java.util.List;

import com.example.entity.Company;
import com.example.entity.Insertcompany;

import org.springframework.stereotype.Service;

@Service
public interface CompanyService2 {

    public long insertCompany(Insertcompany insertcompany);

    public List<Insertcompany> selectCompanyList();

    public int deleteCompany(long insertcompanyno);

    public int updateCompany(Insertcompany insertcompany);

    public Insertcompany selectCompany(long no);

    public List<Insertcompany> selectinsertcompany(String companyid);

}
