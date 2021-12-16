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
public class WishList {

    private Long wishno = 0L;
    private Long product;
    private String company = null;
    private Long wishcount = 0L;
    private Date createtime;
}
