package com.example.demo.src.work.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkCommentReview {
    private int workId;
    private String content;
    private float star;
    private int status;
    private List<String> reviewImg;
}
