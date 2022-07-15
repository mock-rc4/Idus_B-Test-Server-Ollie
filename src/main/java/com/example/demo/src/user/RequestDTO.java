package com.example.demo.src.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class RequestDTO {
    private String targetToken;
    private String title;
    private String body;
}
