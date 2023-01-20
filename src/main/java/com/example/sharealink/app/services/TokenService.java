package com.example.sharealink.app.services;

import com.example.sharealink.app.models.dtos.Token;
import com.example.sharealink.app.models.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Base64;


@Service
public class TokenService {
    ObjectMapper objectMapper = new ObjectMapper();

    public String createToken(User user) {
        String token = objectToJson(
                Token.builder()
                        .username(user.getUsername())
                        .secret(DigestUtils.sha256Hex(user.getPassword()))
                        .build()
        );
        return toBase64(token);
    }
    public String extractUsernameFromToken(String tokenBase64) {
        String tokenJson = fromBase64(tokenBase64);
        Token token = jsonToObject(tokenJson);
        return token.getUsername();
    }

    private String toBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    private  String fromBase64(String str){
        return new String(Base64.getDecoder().decode(str));
    }

    private String objectToJson(Token tokenObject) {
        try {
            return objectMapper.writeValueAsString(tokenObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Token jsonToObject(String tokenJson) {
        try {
            return objectMapper.readValue(tokenJson, Token.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
