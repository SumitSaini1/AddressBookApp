package com.addressbook.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.addressbook.app.model.User;
import com.addressbook.app.repository.UserRepository;
import com.addressbook.app.dto.LoginResponseDto;
import com.addressbook.app.dto.SignupRequestDto;
import com.addressbook.app.dto.SignupResponseDto;
import com.addressbook.app.dto.LoginRequestDto;
import com.addressbook.app.model.type.AuthProviderType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.RequiredArgsConstructor;

import com.addressbook.app.security.AuthUtil;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

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
                        loginDto.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public SignupResponseDto signUp(SignupRequestDto signUpResponseDto) {

        User user = userRepo.findByUsername(signUpResponseDto.getUsername()).orElse(null);

        if (user != null) {
            throw new IllegalArgumentException("Username is already exist");
        }

        user = userRepo.save(
                User.builder()
                        .username(signUpResponseDto.getUsername())
                        .password(encoder.encode(signUpResponseDto.getPassword()))
                        .role("ROLE_USER")
                        .build());

        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    public User signUpInternal(SignupRequestDto signupRequestDto,
            AuthProviderType authProviderType,
            String providerId) {

        User existing = userRepo.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if (existing != null) {
            return existing;
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .role("ROLE_USER")
                .build();

        if (authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(encoder.encode(signupRequestDto.getPassword()));
        }

        return userRepo.save(user);
    }

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto, AuthProviderType.EMAIL, null);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User,
            String registrationId) {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        String email = oAuth2User.getAttribute("email");

        User user = userRepo.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        if (user == null) {

            User emailUser = userRepo.findByUsername(email).orElse(null);

            if (emailUser != null) {
                user = emailUser;
            
                user.setProviderId(providerId);
                user.setProviderType(providerType);
            
                userRepo.save(user);
            } else {
                String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);

                SignupRequestDto dto = new SignupRequestDto();
                dto.setUsername(username);

                user = signUpInternal(dto, providerType, providerId);
            }
        }

        String token = authUtil.generateAccessToken(user);

        return ResponseEntity.ok(new LoginResponseDto(token, user.getId()));
    }
}