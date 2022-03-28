package com.example.demo.src.offclass.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OffclassList {

    private int offlineId;
    private int authorId;
    private String img;
    private String city;
    private String town;
    private String category;
    private String title;
    private float star;
    private int statCnt;
}
