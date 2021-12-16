package com.example.entity;

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
@Table(name = "Options")
@SequenceGenerator(name = "SEQ_PRODUCT_NO", sequenceName = "SEQ_PRODUCT_NO", allocationSize = 1, initialValue = 1)
public class Option {

    @Id
    @Column(name = "OPTION_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPTION_NO")
    private Long optionno;

    @Column(name = "OPTION_NAME")
    private String optionname;

    @Column(name = "OPTION_COLOR")
    private String optioncolor;

    @Column(name = "OPTION_SIZE")
    private String optionsize;

    @Column(name = "OPTION_PRICE")
    private Long optionprice;

    @Column(name = "OPTION_QUANTITY")
    private Long optionquantity;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;
}
