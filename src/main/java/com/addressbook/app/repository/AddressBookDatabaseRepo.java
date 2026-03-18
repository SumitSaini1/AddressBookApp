package com.addressbook.app.repository;
import com.addressbook.app.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.*;

@Repository
public interface AddressBookDatabaseRepo extends JpaRepository<Contact,Long>{
    Optional<Contact> findByFirstName(String firstName);
    boolean existsByFirstNameAndLastName(String firstName,String LastName);

    List<Contact> findByCityIgnoreCase(String city);
    List<Contact> findByStateIgnoreCase(String city);

    

    
}
