package com.example.service;

import java.util.List;

import com.example.entity.Board;
import com.example.entity.Company;
import com.example.entity.SellerBoard;
import com.example.entity.ShopBoard;
import com.example.repository.BoardRepository;
import com.example.repository.CompanyRepository;
import com.example.repository.CompanyRepository2;
import com.example.repository.SellerBoardRepository;
import com.example.repository.ShopBoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository bRepository;

    @Autowired
    SellerBoardRepository sRepository;

    @Autowired
    ShopBoardRepository shopRepository;

    @Autowired
    CompanyRepository cRepository;

    // 공지사항 -----------------------------------------------------------
    @Override
    public long insertBoard(Board board) {
        try {
            Board ret = bRepository.save(board);
            return ret.getNo();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Board> selectBoard() {
        return bRepository.findAll();
    }

    @Override
    public int deleteBoard(long no) {
        bRepository.deleteById(no);
        return 0;
    }

    @Override
    public int updateBoard(Board board) {
        bRepository.save(board);
        return 0;
    }

    @Override
    public Board selectoneBoard(long no) {
        return bRepository.findByNo(no);

    }

    @Override
    public int updatehit(Board board) {
        try{
            bRepository.save(board);
        return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 판매QNA -----------------------------------------------------------
    @Override
    public long insertSellerBoard(SellerBoard sellerBoard) {
        try{
        SellerBoard ret = sRepository.save(sellerBoard);
            return ret.getSellerboardno();
        }
            catch(Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public List<SellerBoard> selectsellerBoards() {
        try{
            return sRepository.findAll();
        }
            catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteSellerBoard(long sellerboardno) {
        try{
            sRepository.deleteById(sellerboardno);
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateSellerBoard(SellerBoard sellerBoard) {
        try{
            sRepository.save(sellerBoard);
            return 0;
        }
        catch(Exception e){
            return -1;
        }
    }

    @Override
    public SellerBoard selectOneSellerBoard(long sellerboardno) {
        try{
            return sRepository.findBySellerboardno(sellerboardno);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updatesellerboardhit(SellerBoard sellerBoard) {
        try{   
            sRepository.save(sellerBoard);
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    // 쇼핑QNA -----------------------------------------------------------
    @Override
    public long insertShopBoard(ShopBoard shopBoard) {
        try{
            ShopBoard ret = shopRepository.save(shopBoard);
            return ret.getShopboardno();
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<ShopBoard> selectShopBoards() {
        try{
            return shopRepository.findAll();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteShopBoard(long shopboardno) {
        try{
            shopRepository.deleteById(shopboardno);
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateShopBoard(ShopBoard shopBoard) {
        try{
            shopRepository.save(shopBoard);
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ShopBoard selectOneShopBoard(long shopboardno) {
        try{
            return shopRepository.findByShopboardno(shopboardno);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    
    @Override
    public int updateshopboardhit(ShopBoard shopBoard) {
        try{
            shopRepository.save(shopBoard);
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    // 유저정보 LIST
    @Override
    public List<Company> selectCompanys() {
        try{
            return cRepository.findAll();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }






}
