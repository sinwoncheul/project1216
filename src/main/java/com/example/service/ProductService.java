package com.example.service;

import java.util.List;

import javax.crypto.interfaces.PBEKey;

import com.example.entity.Product;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    // 물품 등록
    public Product insertProduct(Product product);

    // 물품전체조회
    public List<Product> selectProductList();

    // 개인상품조회
    public List<Product> selectMyProductList(String id);

    public int deleteItem(long no);

    public int updateItem(Product product);

    public Product selectproductid(String id);

    public Product selectItemAlone(Long id);

    public int updatehit(Product product);

}
