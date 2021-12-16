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
@SequenceGenerator(name = "SEQ_INSERTCOMPANY_NO", sequenceName = "SEQ_INSERTCOMPANY_NO", allocationSize = 1, initialValue = 1)
@NoArgsConstructor

@Table(name = "Insertcompany")

public class Insertcompany {
    @Id
    @Column(name = "INSERTCOMPANY_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INSERTCOMPANY_NO")
    private Long insertcompanyno = 0L;

    @Lob
    @Column(name = "INSERTCOMPANY_NAME")
    private String insertcompanyname = null;

    @Lob
    @Column(name = "INSERTCOMPANY_ADDRESS")
    private String insertcompanyaddress = null;

    @Lob
    @Column(name = "INSERTCOMPANY_TEL")
    private String insertcompanytel = null;

    @Lob
    @Column(name = "INSERTCOMPANY_CONTETNT")
    private String insertcompanycontent = null;

    @Column(name = "INSERTCOMPANY_WISHHIT") // 기업 내 상품 좋아요 총 합계(규)
    private int insertcompanywishhit;

    @Column(name = "INSERTCOMPANY_DATE")
    @CreationTimestamp
    private Date insertcompanydate = null;

    @ManyToOne
    @JoinColumn(name = "COMPANY")
    private Company company;

}
