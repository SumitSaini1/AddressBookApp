package com.addressbook.app.model;
import com.addressbook.app.model.type.AuthProviderType;
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

    @Column(unique=true,nullable=true)
    private String username;

    @Column(nullable = true)
    private String password;

    private String role;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    

}
