package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "ORDERPRODUCT")
@SequenceGenerator(name = "SEQ_ORDERPRODUCT", sequenceName = "SEQ_ORDERPRODUCT_NO", initialValue = 1, allocationSize = 1)
public class OrderProduct {
    @Id
    @Column(name = "ORDER_PRODUCT_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERPRODUCT")
    private Long orderproductno = null;

    @Lob
    @Column(name = "ORDER_PRODUCT_COUNT")
    private Long orderproductcount = 0L;

    @Lob
    @Column(name = "ORDER_SUBTOTAL_PRICE")
    private Long ordersubtotalprice = 0L;

    @Column(name = "ORDER_CNT")
    private Long ordercnt = 0L;


    // 회원
    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company company;

    // 주문번호
    @ManyToOne
    @JoinColumn(name = "ORDER_NO")
    private Order order;

    // 상품내용
    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

}
