package com.addressbook.app.dto;

import lombok.*;

@Data
@Getter

public class SignupRequestDto {
    private String username;
    private String password;

}
