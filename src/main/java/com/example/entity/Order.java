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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor

@Table(name = "ORDERS")
@SequenceGenerator(name = "SEQ_ORDER_NO", sequenceName = "SEQ_ORDER_NO", initialValue = 1, allocationSize = 1)

public class Order {
    @Id
    @Column(name = "ORDER_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_NO")
    private Long orderno = 0L;

    @Column(name = "ORDER_PRICE")
    private Long orderprice = 0L;

    @Column(name = "ORDER_STATE")
    private String orderstate = null;
    // 결제전 N 결제후 Y

    @Column(name = "ORDER_CNT")
    private Long ordercnt = 0L;

    @Column(name = "ORDER_ADDRESS")
    private Long orderaddress = 0L;

    @Column(name = "ORDER_DATE")
    @CreationTimestamp
    private Date orderdate;

    // 주문 회원
    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    // 상품내용
    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "OPTION_NO")
    private Option option;

}
