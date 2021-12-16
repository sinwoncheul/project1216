package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "CART")
@SequenceGenerator(name = "SEQ_CART", sequenceName = "SEQ_CART_NO", initialValue = 1, allocationSize = 1)
public class Cart {

    @Id
    @Column(name = "CART_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART")
    private Long cartno = 0L;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company = null;

    @Column(name = "CART_COUNT")
    private Long cartcount;

    @Column(name = "CART_PRICE") // 장바구니 가격
    private Long cartprice = null;

}
