package com.addressbook.app.repository;

import java.util.*;
import com.addressbook.app.model.AddressBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.addressbook.app.dto.ContactDto;
@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook,Long> {
	Optional<AddressBook> findByName(String name);

	
	
}
