package com.example.demo.src.work.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkDetail {
    private int workId;
    private int authorId;
    private String category;
    private String title;
    private int price;
    private int delivery_price;
    private String delivery_start;
    private String quantity;
    private String content;
    private int interestStatus;
    private int starCnt;
    private float star;

    private List<String> keyword;
    private List<String> imgs;

   private List<GetWorkComment> workComment;
   private List<GetWorkReviewRes> workReview;


    public void setWorkDetail(GetWorkDetailRes getWorkDetailRes){
        this.setWorkId(getWorkDetailRes.getWorkId());
        this.setAuthorId(getWorkDetailRes.getAuthorId());
        this.setCategory(getWorkDetailRes.getCategory());
        this.setTitle(getWorkDetailRes.getTitle());
        this.setPrice(getWorkDetailRes.getPrice());
        this.setDelivery_price(getWorkDetailRes.getDelivery_price());
        this.setDelivery_start(getWorkDetailRes.getDelivery_start());
        this.setQuantity(getWorkDetailRes.getQuantity());
        this.setContent(getWorkDetailRes.getContent());
        this.setInterestStatus(getWorkDetailRes.getInterestStatus());
        this.setStarCnt(getWorkDetailRes.getStarCnt());
        this.setStar(getWorkDetailRes.getStar());
    }
}
