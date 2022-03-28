package com.example.demo.src.offclass.model;
import com.example.demo.src.work.model.GetWorkComment;
import com.example.demo.src.work.model.GetWorkReviewRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffClassDetail {
    private int offlineId;
    private int authorId;
    private String category;
    private String title;
    private int price;
    private String courseTime;
    private String difficulty;
    private String peopleMax;
    private String city;
    private String town;
    private String content;
    private int interestStatus;
    private List<String> imgs;


    private List<GetWorkComment> offlineComment;
    private List<GetWorkReviewRes> offlineReview;

    public void setOfflineDetail(OffClassDetailBase onClassDetailBase) {
        this.offlineId=onClassDetailBase.getOfflineId();
        this.authorId=onClassDetailBase.getAuthorId();

        this.category=onClassDetailBase.getCategory();
        this.title=onClassDetailBase.getTitle();
        this.price=onClassDetailBase.getPrice();
        this.courseTime=onClassDetailBase.getCourseTime();
        this.difficulty=onClassDetailBase.getDifficulty();
        this.peopleMax=onClassDetailBase.getPeopleMax();
        this.city=onClassDetailBase.getCity();
        this.town=onClassDetailBase.getTown();
        this.content=onClassDetailBase.getContent();
        this.interestStatus=onClassDetailBase.getInterestStatus();
    }
}
