package com.addressbook.app.dto;

import lombok.*;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String jwt;
    Long id;
}
