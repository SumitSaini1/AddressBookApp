package com.addressbook.app.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();


}
