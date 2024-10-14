package org.zerock.api1014.cart.dto;

import lombok.Data;

@Data
public class CartDetailsListDTO {

    private Long pno;
    private String panme;
    private int price;
    private Long reviewCnt;
    private String fileName;
    private int qty;

    public CartDetailsListDTO(Long pno, String panme, int price, Long reviewCnt, String fileName, int qty) { // 헷갈리지 않기위해 직접작성
        this.pno = pno;
        this.panme = panme;
        this.price = price;
        this.reviewCnt = reviewCnt;
        this.fileName = fileName;
        this.qty = qty;
    }
}
