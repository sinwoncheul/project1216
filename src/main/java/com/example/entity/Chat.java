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
@NoArgsConstructor
@ToString

@Entity // 엔티티 => 자동으로 테이블 생성
@Table(name = "CHAT") // 테이블 명
@SequenceGenerator(name = "SEQ_CHAT", sequenceName = "SEQ_CHAT_NO", initialValue = 1, allocationSize = 1)

public class Chat {

    @Column(name = "CHATID") // 컬럼명
    @Id // 기본키(중복X)
    // 여기 추가되는 정보는 위에 생성한 시퀀스 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAT")
    private Long chatid = 0L; // 글번호

    @CreationTimestamp // 날짜는 자동으로 추가
    @Column(updatable = false, name = "REGDATE")
    private Date regdate = null; // 날짜

    @ManyToOne
    @JoinColumn(name = "ADMINID")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "COMPANYID")
    private Company company;

}