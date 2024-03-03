package com.example.domain.request;

import java.util.List;

public class TimeRequest {
    private String email;
    private List<String> times;

    public String getEmail() {
        return email;
    }

    public List<String> getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "RequestTimeRequest{" +
                "email='" + email + '\'' +
                ", times=" + times +
                '}';
    }
}
