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

@Getter
@Setter
@ToString
@Entity
@SequenceGenerator(name = "SEQ_PRODUCTREVIEW", sequenceName = "SEQ_PRODUCTREVIEW_NO", allocationSize = 1, initialValue = 1)
@NoArgsConstructor

@Table(name = "ProductReview")

public class ProductReview {
    @Id
    @Column(name = "REVIEW_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTREVIEW_NO")
    private Long reviewno = 0L;

    @Column(name = "REVIEW_NAME")
    private String reviewname = null;

    @Column(name = "WRITER_CONTENT")
    private String writercontent = null;

    @Column(name = "REVIEW_DATE")
    @CreationTimestamp
    private Date reviewdate = null;

    @Column(name = "REVIEW_CHK")
    private Long reviewchk = null;

    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

}
