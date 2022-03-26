package com.example.demo.src.work.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetWorkNewRes {
    private int workId;
    private int author_id;
    private String title;
    private String workImg;
    private int interestStatus;
}
