package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "COMPANY")
@NoArgsConstructor
public class Company {

    @Id
    @Column(name = "COMPANY_ID")
    private String companyid;

    @Column(name = "PASSWORD")
    private String password = null;

    @Transient
    private String newpassword;

    @Column(name = "COMPANY_NO")
    private Long companyno = null;

    @Column(name = "PHONE")
    private String phone = null;

    @Column(name = "COMPANY_NAME")
    private String companyname = null;

    @Column(name = "COUNTRY")
    private String country = null;

    @Column(name = "CITY")
    private String city = null;

    @Column(name = "ROLE")
    private String role = null;

    @Column(name = "COMPANY_CART_HIT")
    private int companycarthit = 0;

    @Column(name = "CREATETIME", updatable = false)
    @CreationTimestamp
    private Date createtime;
}
