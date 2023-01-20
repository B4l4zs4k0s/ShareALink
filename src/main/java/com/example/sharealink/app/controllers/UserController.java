package com.example.sharealink.app.controllers;

import com.example.sharealink.app.models.dtos.LoginRequest;
import com.example.sharealink.app.models.dtos.RegisterRequest;
import com.example.sharealink.app.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "registration-page";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute(name ="register") @RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/")
    public String login(@ModelAttribute(name="auth") @RequestBody LoginRequest request, HttpServletResponse httpResponse){
      String token = userService.login(request);
      httpResponse.addCookie(new Cookie("token",token));
        return "redirect:/feed/1";
    }
}
