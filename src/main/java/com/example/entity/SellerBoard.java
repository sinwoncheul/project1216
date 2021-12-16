package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity // 엔티티 => 자동으로 테이블 생성
@Table(name = "SELLERBOARD") // 테이블 명
@SequenceGenerator(name = "SEQ_SELLERBOARD", sequenceName = "SEQ_SELLERBOARD_NO", initialValue = 1, allocationSize = 1)

public class SellerBoard {

    @Column(name = "SELLERBOARDNO") // 컬럼명
    @Id // 기본키(중복X)
    // 여기 추가되는 정보는 위에 생성한 시퀀스 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SELLERBOARD")
    private Long sellerboardno = 0L; // 글번호

    @Column(name = "SELLERTITLE")
    private String sellertitle = null; // 글제목

    @Lob
    @Column(name = "SELLERCONTENT")
    private String sellercontent = null; // 내용

    @Column(name = "SELLERWRITER")
    private String sellerwriter = null; // 작성자

    @Column(name = "SELLERHIT")
    private int sellerhit = 1; // 조회수

    @CreationTimestamp // 날짜는 자동으로 추가
    @Column(updatable = false, name = "REGDATE")
    private Date sellerregdate = null; // 날짜

}