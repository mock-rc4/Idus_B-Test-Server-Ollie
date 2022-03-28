package com.example.demo.src.offclass.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OffClassDetailBase {
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

}
