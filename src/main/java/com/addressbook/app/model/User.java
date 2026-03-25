package com.addressbook.app.model;

import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true,nullable=false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    

}
