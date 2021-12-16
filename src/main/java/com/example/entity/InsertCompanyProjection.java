package com.example.entity;

import java.util.Date;

public interface InsertCompanyProjection {

    long getinsertcompanyno();

    String getinsertcompanyname();

    String getinsertcompanyaddress();

    String getinsertcompanyABOUTUS();

    String getinsertcompanycontent();

    int getHit(); // 기업 내 상품 좋아요 총 합계(규)

    Date getRegdate();

}
