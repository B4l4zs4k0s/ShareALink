package com.example.sharealink.app.models.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
