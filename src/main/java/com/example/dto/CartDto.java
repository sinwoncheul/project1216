package com.example.dto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDto {

    private Long cartno = 0L; // 장바구니 번호
    private Long product; // 상품 번호
    private Long cartcount = null; // 장바구니 갯수
    private Long cartsubtotalprice = null; // 1개 장바구니 총 금액
    private String company; // 회원 번호
    private String cartitemtitle = null; // 장바구니 상품내용
    private Long cartprice = 0L; // 장바구니 가격

}
