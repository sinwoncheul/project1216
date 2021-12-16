package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "PRODUCTDETAIL")
@SequenceGenerator(name = "SEQ_PRODUCTDETAIL_NO", sequenceName = "SEQ_PRODUCTDETAIL_NO", allocationSize = 1, initialValue = 1)
public class ProductDetail {

    @Id
    @Column(name = "PRODUCT_NAME_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTDETAIL_NO")
    private Long productnameno;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

}
