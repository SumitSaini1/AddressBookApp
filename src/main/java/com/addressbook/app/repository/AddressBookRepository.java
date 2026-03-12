package com.addressbook.app.repository;

import java.util.*;
import com.addressbook.app.model.Contact;
import org.springframework.stereotype.Repository;
import com.addressbook.app.dto.ContactDto;
@Repository
public class AddressBookRepository {
	ArrayList<Contact> contacts=new ArrayList<>();
	public String addContact(Contact contact) {
		contacts.add(contact);


	    return "Contact Added";
	}
	
	public String updateByName(String firstName, Contact updatedContact) {

	    if (contacts.isEmpty()) {
	        return "Contact List is Empty";
	    }

	    for (Contact contact : contacts) {

	        if (contact.getFirstName().equalsIgnoreCase(firstName)) {

	            contact.setLastName(updatedContact.getLastName());
	            contact.setAddress(updatedContact.getAddress());
	            contact.setCity(updatedContact.getCity());
	            contact.setState(updatedContact.getState());
	            contact.setZip(updatedContact.getZip());
	            contact.setPhoneNumber(updatedContact.getPhoneNumber());
	            contact.setEmail(updatedContact.getEmail());

	            return "Successfully Updated";
	        }
	    }

	    return "No contact Found By This Name";
	}
	
	public List<Contact> showAllContacts(){
		return contacts;
	}
	
	public String deleteContactByName(String firstName) {
		if(contacts.isEmpty()) {
			 return "Contact List is Empty";
		}
		for(int i=0;i<contacts.size();i++) {
			Contact contact =contacts.get(i);
			if(contact.getFirstName().equals(firstName)) {
				contacts.remove(i);
				return "Contact Deleted SuccessFully";
				
			}
			
		}
		return "No Contact Found By Name";
		
		
	}
	

	
}
