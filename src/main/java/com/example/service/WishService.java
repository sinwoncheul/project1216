package com.example.service;

import java.util.List;

import com.example.dto.WishList;

import org.springframework.stereotype.Service;

@Service
public interface WishService {

    // 위시리스트 상품 조회
    public List<WishList> wishListselect(String id);

    // 위시리스트 상품 삭제
    public int wishListdelete(Long id);

    // 위시리스트 상품 수정
    public int wishListupdate(WishList wList);

    // 위시리스트 카운트 조회
    public int wishcountselect(String id);

    // 위시리스트 상품코드 조회
    public int selectCartkey(Long id);

    // 위시리스트 기업별 토탈 합계 조회
    public List<WishList> companycarthit();

}
