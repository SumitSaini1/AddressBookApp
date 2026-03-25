package com.addressbook.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.addressbook.app.service.AuthService;
import com.addressbook.app.dto.LoginResponseDto;
import com.addressbook.app.dto.SignupRequestDto;
import com.addressbook.app.dto.LoginRequestDto;
import com.addressbook.app.dto.SignupResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestLoginDto){
        LoginResponseDto responseDto=authService.login(requestLoginDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);



    }
    @PostMapping("/signup")
    ResponseEntity<SignupResponseDto> signUp(@RequestBody SignupRequestDto signUpRequestDto){
        SignupResponseDto responseDto=authService.signUp(signUpRequestDto);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);

        
    }

    
}
