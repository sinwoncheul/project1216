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

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "PRODUCT")
@SequenceGenerator(name = "SEQ_PRODUCT_NO", sequenceName = "SEQ_PRODUCT_NO", allocationSize = 1, initialValue = 1)
public class Product {

    @Id
    @Column(name = "PRODUCT_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_NO")
    private Long productno;

    @Column(name = "PRODUCT_TITLE")
    private String producttitle;

    @Column(name = "PRODUCT_CONTENT")
    private String productcontent;

    @Column(name = "PRODUCT_TOTALPRICE")
    private Long producttotalprice;

    @Column(name = "PRODUCT_QUANTITY")
    private Long productquantity;

    @Column(name = "DELIVERYDATE")
    private String deliverydate;

    @Column(name = "DELIVERYPAY")
    private String deliverypay;

    @Column(name = "PRODUCT_DATE")
    @CreationTimestamp
    private Date productdate = null;

    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "INSERTCOMPANY_NO")
    private Insertcompany insertcompany;

}
