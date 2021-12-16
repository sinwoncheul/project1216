package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.dto.CartDto;
import com.example.entity.Cart;
import com.example.entity.Company;
import com.example.repository.CartRepository;
import com.example.repository.CompanyRepository;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public int CartInsert(CartDto cart) {
        try {
            return sqlSessionFactory.openSession().insert("Cart.CartInsert", cart);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Cart> selectCart(String id) {
        try {
            return sqlSessionFactory.openSession().selectList("Cart.selectCart", id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteCart(String id) {
        return sqlSessionFactory.openSession().delete("Cart.seleCart", id);
    }

    @Override
    public int updateCart(Cart cart) {
        return sqlSessionFactory.openSession().update("Cart.seleCart", cart);
    }

}
