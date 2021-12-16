package com.example.service;

import java.util.Collection;

import com.example.entity.Company;
import com.example.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompanyDetailsService implements UserDetailsService {

    @Autowired
    CompanyRepository cRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println("companyid" + id);
        Company company = cRepository.findById(id).orElse(null);

        if (company != null) {
            System.out.println(company.getCompanyid() + company.getPassword() + "roles");

            String[] role = { company.getRole() };
            Collection<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);

            User user = new User(company.getCompanyid(), company.getPassword(), roles);
            System.out.println(company.getCompanyid());
            System.out.println(company.getPassword());
            System.out.println(roles);
            return user;
        }
        return null;
    }

}
