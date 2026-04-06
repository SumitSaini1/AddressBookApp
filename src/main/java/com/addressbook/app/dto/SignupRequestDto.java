package com.addressbook.app.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data

public class SignupRequestDto {
    @NotBlank
    private String username;
    private String password;
    private String role;

}
