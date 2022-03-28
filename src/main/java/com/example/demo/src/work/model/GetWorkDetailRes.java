package com.example.demo.src.work.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetWorkDetailRes {
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
}
