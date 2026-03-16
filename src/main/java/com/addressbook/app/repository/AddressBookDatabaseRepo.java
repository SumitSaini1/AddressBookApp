package com.addressbook.app.repository;
import com.addressbook.app.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AddressBookDatabaseRepo extends JpaRepository<Contact,Long>{
    Optional<Contact> findByFirstName(String firstName);
    
}
