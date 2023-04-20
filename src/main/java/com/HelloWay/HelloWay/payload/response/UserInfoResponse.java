package com.HelloWay.HelloWay.payload.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class UserInfoResponse {
    private Long id;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private  String phone;
    private String username;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String name, String lastname, LocalDate birthday, String phone, String username, String email, List<String> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.phone = phone ;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
