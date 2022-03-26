package com.example.demo.src.onclass.model;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkDetailRes;
import com.example.demo.src.work.model.GetWorkReviewRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OnClassDetail {
    private int authorId;
    private String video;
    private String category;
    private String title;
    private int price;
    private String courseStart;
    private String difficulty;
    private String materialExplain;
    private String content;
    private int interestStatus;
    private int starCnt;
    private float star;

    private List<GetWorkComment> onlineComment;
    private List<GetWorkReviewRes> onlineReview;

    public void setOnlineDetail(OnClassDetailBase onClassDetailBase) {
        this.authorId=onClassDetailBase.getAuthorId();
        this.video=onClassDetailBase.getVideo();
        this.category=onClassDetailBase.getCategory();
        this.title=onClassDetailBase.getTitle();
        this.price=onClassDetailBase.getPrice();
        this.courseStart=onClassDetailBase.getCourseStart();
        this.difficulty=onClassDetailBase.getDifficulty();
        this.materialExplain=onClassDetailBase.getMaterialExplain();
        this.content=onClassDetailBase.getContent();
        this.interestStatus=onClassDetailBase.getInterestStatus();
        this.starCnt=onClassDetailBase.getStarCnt();
        this.star=onClassDetailBase.getStar();
    }
}
