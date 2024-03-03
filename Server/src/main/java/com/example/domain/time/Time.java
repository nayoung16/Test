package com.example.domain.time;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "TIME")
@NoArgsConstructor
@Entity
public class Time {

    @Id
    @Column(name = "time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time")
    private String time;

    @Column(name="user_email")
    private String user_email;


    @Builder
    public Time(String time, String user_email) {
        this.time = time;
        this.user_email = user_email;
    }
}