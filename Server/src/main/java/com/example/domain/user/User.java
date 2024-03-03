package com.example.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Table(name = "USER")
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "gender")
    private String gender;

    @Column(name = "locationsido")
    private String locationsido;

    @Column(name = "locationgu")
    private String locationgu;

    @Column(name = "tutoringmethod")
    private String tutoringmethod;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "photourl")
    private String photourl;

    @Builder
    public User(String email, String name, String role, String gender, String locationsido,
                String locationgu, String tutoringmethod, String introduction, String photourl) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.locationsido = locationsido;
        this.locationgu = locationgu;
        this.tutoringmethod = tutoringmethod;
        this.introduction = introduction;
        this.photourl = photourl;
    }

    //마이페이지 업데이트
    public void updateMypage(String gender, String locationsido, String locationgu, String tutoringmethod, String introduction){
        this.gender = gender;
        this.locationsido = locationsido;
        this.locationgu =locationgu;
        this.tutoringmethod = tutoringmethod;
        this.introduction = introduction;
    }
}