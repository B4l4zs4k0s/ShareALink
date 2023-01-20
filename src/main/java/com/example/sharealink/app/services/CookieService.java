package com.example.sharealink.app.services;

import com.example.sharealink.app.exception.UnauthorizedUserException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CookieService {

    private final TokenService tokenService;

    public CookieService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String getCookieValue(HttpServletRequest httpRequest) {
        Cookie cookie = Arrays.stream(httpRequest.getCookies())
                .filter(x -> x.getName().equals("token"))
                .findAny()
                .orElseThrow(UnauthorizedUserException::new);
        return cookie.getValue();
    }

    public String getNameFromCookie(HttpServletRequest httpRequest) {
        Cookie cookie = Arrays.stream(httpRequest.getCookies())
                .filter(x -> x.getName().equals("token"))
                .findAny()
                .orElseThrow();
       return  tokenService.extractUsernameFromToken(cookie.getValue());
    }
}
