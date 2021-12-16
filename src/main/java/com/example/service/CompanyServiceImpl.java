package com.example.service;

import java.util.Optional;

import com.example.entity.Company;
import com.example.repository.CompanyRepository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository cRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public int insertCompanyUser(Company company) {
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            company.setPassword(bcpe.encode(company.getPassword()));

            cRepository.save(company);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateCompanyUser(Company company) {
        cRepository.save(company);
        return 0;
    }

    @Override
    public int deleteCompanyUser(String companyid) {
        cRepository.deleteById(companyid);
        return 0;
    }

    @Override
    public Company selectCompanyUserOne(String id) {
        Optional<Company> company = cRepository.findById(id);
        return company.orElse(null); // 없으면 null 리턴
    }

    @Override
    public int loginCompanyUser(Company company, int type) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(company.getCompanyid(), company.getPassword()));
            String roles = authentication.getAuthorities().iterator().next().toString(); // 권한값추가
            // System.out.println(roles);
            // System.out.println(type);
            if (type == 1) {
                if (roles.equals("USER")) {
                    return 1; // 타입일치시 1
                }
            } else if (type == 2) {
                if (roles.equals("SELLER")) {
                    return 1;
                }
            }
            return 0; // 권한 불일치 시 0
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 아이디가 없을시 -1
        }
    }

    @Override
    public int idcheck(String companyid) {
        try {
            return sqlSessionFactory.openSession().selectOne("Company.idCheck", companyid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Company mylist(String id) {

        return null;
    }

    // 조회수(위시리스트) 증가,감소
    @Override
    public int updatehit(int type) {
        try {
            if (type == 1) {
                return 1;
            } else if (type == 2) {
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
