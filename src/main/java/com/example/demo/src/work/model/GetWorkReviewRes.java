package com.example.demo.src.work.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetWorkReviewRes {
    private String name;
    private float star;
    private String createdAt;
    private String content;
    private String reviewImg;
}
