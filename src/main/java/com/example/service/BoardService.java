package com.example.service;

import java.util.List;

import com.example.entity.Board;
import com.example.entity.Company;
import com.example.entity.SellerBoard;
import com.example.entity.ShopBoard;

import org.springframework.stereotype.Service;

@Service
public interface BoardService {

    // 공지사항
    // 글 추가
    public long insertBoard(Board board);
    
    // 글 목록
    public List<Board> selectBoard();
    
    // 글 삭제
    public int deleteBoard(long no);
    
    // 글 수정
    public int updateBoard(Board board);
    
    // 글 1개 조회
    public Board selectoneBoard(long no);

    // 글 조회수 1증가
    public int updatehit(Board board);

    // 판매QNA
    public long insertSellerBoard(SellerBoard sellerBoard);

    public List<SellerBoard> selectsellerBoards();

    public int deleteSellerBoard(long sellerboardno);

    public int updateSellerBoard(SellerBoard sellerBoard);

    public SellerBoard selectOneSellerBoard(long sellerboardno);

    public int updatesellerboardhit(SellerBoard sellerBoard);

    // 쇼핑QNA
    public long insertShopBoard(ShopBoard shopBoard);

    public List<ShopBoard> selectShopBoards();

    public int deleteShopBoard(long shopboardno);

    public int updateShopBoard(ShopBoard shopBoard);

    public ShopBoard selectOneShopBoard(long shopboardno);

    public int updateshopboardhit(ShopBoard shopBoard);

    
    // 유저정보
    public List<Company> selectCompanys();
    
}
