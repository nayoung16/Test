package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAndTimeReturnDto {
    private String email;
    private String name;
    private String role;
    private String gender;
    private String locationsido;
    private String locationgu;
    private String tutoringmethod;
    private String introduction;
    private String photourl;
    private List<String> times;

    public UserAndTimeReturnDto(String email, String name, String role, String gender, String locationsido, String locationgu, String tutoringmethod, String introduction, String photourl, List<String> times) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.locationsido = locationsido;
        this.locationgu = locationgu;
        this.tutoringmethod = tutoringmethod;
        this.introduction = introduction;
        this.photourl = photourl;
        this.times = times;
    }
}
