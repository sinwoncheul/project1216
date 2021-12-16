package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "admin") // 테이블 명


// 관리자번호, 가입일, 게시판번호
public class Admin {

    @Id
    @Column(name = "ADMINID")
    private String adminid = null;

    @CreationTimestamp // 날짜는 자동으로 추가
    @Column(updatable = false, name = "ADMINREGDATE")
    private Date adminregdate = null; // 날짜

    @ManyToOne
    @JoinColumn(name = "BOARDNO")
    private Board board;

}