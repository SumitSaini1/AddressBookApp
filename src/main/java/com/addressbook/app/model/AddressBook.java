package com.addressbook.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AddressBook {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
   

    

    
}
