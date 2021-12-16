package com.example.service;

import java.util.List;

import com.example.entity.Order;
import com.example.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl implements OrderProductService{

    @Autowired
    OrderRepository oRepository;

    @Override
    public Order insertorder(Order order) {
        try{
            return oRepository.save(order);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateorder(Order order) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Order> selectorder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteorder(long no) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Order selectoneorder(long no) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
