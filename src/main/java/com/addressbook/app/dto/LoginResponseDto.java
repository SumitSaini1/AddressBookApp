package com.addressbook.app.dto;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String jwt;
    Long id;
}
