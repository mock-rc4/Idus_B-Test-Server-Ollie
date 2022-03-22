package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String recommendedCode;
    private int receivingConsent;
    private int status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String social;
    private String socialId;
    private Date birthday;
    private int gender;
    private String recipient;
    private String recipientPhone;
    private String address;
    private String profileImg;

}
