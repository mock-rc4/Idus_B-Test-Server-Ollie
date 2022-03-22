package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes { //유저 프로필 정보
    private int id;
    private String email;
    private String name;
    private Date birthday;
    private int gender;
    private String phone;
    private String recipient;
    private String recipientPhone;
    private String address;
    private String profileImg;

    public void setNullProfile(GetUserRes getUserRes){
        if (this.getEmail()==null){
            this.setEmail(getUserRes.getEmail());
        }
        if(this.getName()==null){
            this.setName(getUserRes.getName());
        }
        if(this.getBirthday()==null){
            this.setBirthday(getUserRes.getBirthday());
        }
        if(this.getGender()==0){ //==0으로 해도되나
            this.setGender(getUserRes.getGender());
        }
        if(this.getPhone()==null){
            this.setPhone(getUserRes.getPhone());
        }
        if(this.getRecipient()==null){
            this.setRecipient(getUserRes.getRecipient());
        }
        if(this.getRecipientPhone()==null){
            this.setRecipientPhone(getUserRes.getRecipientPhone());
        }
        if(this.getAddress()==null){
            this.setAddress(getUserRes.getAddress());
        }
        if(this.getProfileImg()==null){
            this.setProfileImg(getUserRes.getProfileImg());
        }
    }
}
