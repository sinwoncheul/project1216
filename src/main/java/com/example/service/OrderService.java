package com.example.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.entity.Order;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository oRepository;
    private final ProductRepository pRepository;

    @Transactional(readOnly = true)
    public String generateOrderNumber() {
        String result = null;
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String to = transFormat.format(from);
        result = "S" + to + UUID.randomUUID().toString();
        return result;
    }

    @Transactional(readOnly = false)
    public Order saveOrder(Order order) {
        oRepository.save(order);
        return order;
    }
}
