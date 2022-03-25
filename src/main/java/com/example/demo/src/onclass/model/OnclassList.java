package com.example.demo.src.onclass.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class OnclassList {
    private int authorId;
    private String category;
    private String title;
    private int price;
    private float star;
    private String review;
    private String name;
    private int interestStatus;
}
