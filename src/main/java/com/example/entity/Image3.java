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
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "BOARDIMG")
@SequenceGenerator(name = "SEQ1_BOARD", sequenceName = "SEQ_COMPANYIMAGE_NO3", initialValue = 1, allocationSize = 1)

public class Image3 {
    @Id // 기본키
    @Column(name = "NO3") // 컬럼명 : Number
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ1_BOARD") // 시퀀스의 값으로 추가됨
    private Long no3;

    @Lob
    @Column(name = "IMAGE") // 물품명 VARCHAR2(100)
    private byte[] image = null;

    @Lob // String 이면 clob, byte[]이면 blob
    @Column(name = "IMAGENAME") // CLOB 길이가 아주 긴 문자열
    private String imagename = null;

    @Column(name = "IMAGESIZE", nullable = false, columnDefinition = "long default 0")
    private Long imagesize;

    @Column(name = "IMAGETYPE")
    private String imagetype = null; // 파일 종류

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @ManyToOne
    @JoinColumn(name = "BOARDNO")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "SELLERBOARDNO")
    private SellerBoard sellerBoard;

    @ManyToOne
    @JoinColumn(name = "SHOPBOARDNO")
    private ShopBoard shopBoard;

}
