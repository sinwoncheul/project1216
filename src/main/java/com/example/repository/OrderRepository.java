package com.example.repository;

import java.util.List;

import com.example.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderno(Long orderno);

    List<Order> findAllByOrderByOrdernoDesc();
}
