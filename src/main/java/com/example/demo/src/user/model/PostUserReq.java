package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String email;
    private String name;
    private String password;
    private String password2;
    private String phone;
    private String recommendedCode;
    private int receivingConsent;
}
