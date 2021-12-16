package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Company;
import com.example.entity.Product;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoductServiceImpl implements ProductService {

    @Autowired
    ProductRepository pRepository;

    @Override
    public Product insertProduct(Product product) { // 물품등록
        try {
            return pRepository.save(product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteItem(long no) { // 삭제하기
        pRepository.deleteById(no);
        return 0;
    }

    @Override
    public int updateItem(Product product) { // 수정하기
        pRepository.save(product);
        return 0;
    }

    @Override
    public List<Product> selectProductList() { // 전체상품 조회
        pRepository.findAll();
        return null;
    }

    @Override
    public List<Product> selectMyProductList(String id) { // 개인상품 조회
        return pRepository.findAllByCompany_companyid(id);
    }

    @Override
    public Product selectItemAlone(Long id) { // 상품 한개 조회
        Optional<Product> product = pRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product selectproductid(String id) {
        pRepository.findByCompany_companyid(id);
        return null;
    }

    @Override
    public int updatehit(Product product) {
        try {
            pRepository.save(product);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // @Override
    // public Product selectItemOne(long no) {
    // return pRepository.findById(no).orElse(null);
    // }

}