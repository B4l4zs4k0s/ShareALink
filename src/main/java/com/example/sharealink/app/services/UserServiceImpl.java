package com.example.sharealink.app.services;

import com.example.sharealink.app.exception.BadCredentialsException;
import com.example.sharealink.app.models.dtos.LoginRequest;
import com.example.sharealink.app.models.dtos.RegisterRequest;
import com.example.sharealink.app.models.entities.User;
import com.example.sharealink.app.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public void registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
      return userRepository.getUserByUsername(username);
    }

    public String login(LoginRequest request){
        User user = authenticate(request);
        return tokenService.createToken(user);
    }

    public User authenticate(LoginRequest request){
        User user;
        try {
            user = userRepository.getUserByUsername(request.getUsername());
        } catch (Throwable e) {
            throw new BadCredentialsException();
        }
        if (!request.getPassword().equals(user.getPassword())){
            throw new BadCredentialsException();
        }
        return user;
    }
}
