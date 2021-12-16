package com.example.service;

import com.example.entity.Company;
import com.example.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service

public interface CompanyService {

    // 회원가입
    public int insertCompanyUser(Company company);

    // 회원수정
    public int updateCompanyUser(Company company);

    // 로그인
    public int loginCompanyUser(Company company, int type);

    // 회원탈퇴
    public int deleteCompanyUser(String companyid);

    // 회원정보가져오기
    public Company selectCompanyUserOne(String id);

    public Company mylist(String id);

    public int idcheck(String companyid);

    // 카트조회수증가
    public int updatehit(int type);

}
