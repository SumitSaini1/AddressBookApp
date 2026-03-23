package com.addressbook.app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Data

public class UserDto {

    private String username;
    private String password;
    private String role;

}
