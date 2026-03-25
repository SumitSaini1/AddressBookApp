package com.addressbook.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.addressbook.app.model.User;
import com.addressbook.app.repository.UserRepository;
import com.addressbook.app.dto.LoginResponseDto;
import com.addressbook.app.dto.SignupRequestDto;
import com.addressbook.app.dto.SignupResponseDto;
import com.addressbook.app.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.addressbook.app.security.AuthUtil;

import io.jsonwebtoken.Jwts;

import org.springframework.security.core.userdetails.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword())

        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String token = authUtil.generateAccessToken(user);
        System.out.println("Generated key " + token);
        return new LoginResponseDto(token, user.getId());

    }

    public SignupResponseDto signUp(SignupRequestDto signUpResponseDto) {
        User user = userRepo.findByUsername(signUpResponseDto.getUsername()).orElse(null);
        if (user != null) {
            throw new IllegalArgumentException("Username is already exist");
        }

        user = userRepo.save(User.builder().username(signUpResponseDto.getUsername())
                .password(encoder.encode(signUpResponseDto.getPassword())).role("ROLE_USER").build());
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

}
