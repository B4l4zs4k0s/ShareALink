package com.example.sharealink.app.services;

import com.example.sharealink.app.models.dtos.LoginRequest;
import com.example.sharealink.app.models.dtos.RegisterRequest;
import com.example.sharealink.app.models.entities.User;

public interface UserService{
    String login(LoginRequest request);
    User authenticate(LoginRequest request);
    void registerUser(RegisterRequest request);
    User findUserByUsername(String name);
}
