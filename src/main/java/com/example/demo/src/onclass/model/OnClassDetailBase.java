package com.example.demo.src.onclass.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class OnClassDetailBase {
    private int authorId;
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

}
