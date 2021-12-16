package com.example.service;

import java.util.List;

import com.example.entity.ProductDetail;

import org.springframework.stereotype.Service;

@Service
public interface ProductDetailService {

    public int insertProduct(ProductDetail productDetail);

    public List<ProductDetail> selectProductList();

    public int deleteProductDetail(long no);

    public int updateProductDetail(ProductDetail ProductDetail);

    public ProductDetail selectProductDetailmOne(long no);

}
