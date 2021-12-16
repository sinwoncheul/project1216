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
@Table(name = "WISH")
@SequenceGenerator(name = "SEQ_WISH_NO", sequenceName = "SEQ_WISH_NO", initialValue = 1, allocationSize = 1)
public class WishList {
    @Id
    @Column(name = "WISH_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WISH")
    private Long wishno = 0L;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company = null;

    @Column(name = "WISH_COUNT")
    private Long wishcount = 0L;

    @ManyToOne
    @JoinColumn(name = "INSERTCOMPANY_NO")
    private Insertcompany insertcompany;

    @Column(name = "INSERTCOMPANY_WISHHIT") // 기업 내 상품 좋아요 총 합계(규)
    private int insertcompanywishhit;

    @Column(name = "CREATE_TIME", updatable = false)
    @CreationTimestamp
    private Date createtime;

}
