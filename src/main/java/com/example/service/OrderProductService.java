package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Order;
import com.example.entity.OrderProduct;
import com.example.entity.Product;
import com.example.entity.ProductDetail;
import com.example.repository.OrderProductRepository;
import com.example.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
// @RequiredArgsConstructor
public interface OrderProductService {

    public Order insertorder(Order order);

    public int updateorder(Order order);

    public List<Order> selectorder();

    public int deleteorder(long no);

    public Order selectoneorder(long no);
// private final OrderProductRepository orderProductRepository;
// private final OrderService orderService;
// private final ProductRepository productRepository;

// @Transactional
// // public void saveOrderProducts(List<Product> products, Order order) {
// // for (ProductDetail pr : products) {
// // OrderProduct orderProduct = new OrderProduct();
// // Optional<Product> productOptional =
// productRepository.findById(pr.getProductnameno());
// // orderProduct.setOrdersubtotalprice(pr.g());
// // orderProduct.setOrderproductcount(pr.getProductquantity());
// // orderProduct.setProduct(productOptional.get());
// // orderProduct.setOrder(order);
// // orderProductRepository.save(orderProduct);
// // }
// // }
}