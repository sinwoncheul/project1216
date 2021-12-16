package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.ProductDetail;
import com.example.repository.ProductDtailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailImpl implements ProductDetailService {

    @Autowired
    ProductDtailRepository pDtailRepository;

    @Override
    public int insertProduct(ProductDetail productDetail) {
        try {
            pDtailRepository.save(productDetail);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ProductDetail> selectProductList() {
        return pDtailRepository.findAll();

    }

    @Override
    public int deleteProductDetail(long no) {
        pDtailRepository.deleteById(no);
        return 0;
    }

    @Override
    public int updateProductDetail(ProductDetail ProductDetail) {
        pDtailRepository.save(ProductDetail);
        return 0;
    }

    @Override
    public ProductDetail selectProductDetailmOne(long no) {
        Optional<ProductDetail> pro = pDtailRepository.findById(no);
        return pro.orElse(null);
    }
}
