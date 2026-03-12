package com.addressbook.app.service;
import com.addressbook.app.model.Contact;
import com.addressbook.app.repository.AddressBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*; 
import com.addressbook.app.dto.ContactDto;
@Service
public class AddressBookService {
	@Autowired
	private AddressBookRepository repo;
	
	public String addContact(ContactDto contactDTO) {

	    Contact contact = new Contact();

	    contact.setFirstName(contactDTO.getFirstName());
	    contact.setLastName(contactDTO.getLastName());
	    contact.setAddress(contactDTO.getAddress());
	    contact.setCity(contactDTO.getCity());
	    contact.setState(contactDTO.getState());
	    contact.setZip(contactDTO.getZip());
	    contact.setPhoneNumber(contactDTO.getPhoneNumber());
	    contact.setEmail(contactDTO.getEmail());

	    return repo.addContact(contact);
	}
	public String updateByName(String firstName, ContactDto updatedContactDto) {

	    Contact contact = new Contact();

	    contact.setLastName(updatedContactDto.getLastName());
	    contact.setAddress(updatedContactDto.getAddress());
	    contact.setCity(updatedContactDto.getCity());
	    contact.setState(updatedContactDto.getState());
	    contact.setZip(updatedContactDto.getZip());
	    contact.setPhoneNumber(updatedContactDto.getPhoneNumber());
	    contact.setEmail(updatedContactDto.getEmail());

	    return repo.updateByName(firstName, contact);
	}
	
	public List<Contact> showAllContacts(){
		return repo.showAllContacts();
	}
	public String deleteContactByName(String firstName) {
		if(firstName==null || firstName.isEmpty()) {
			return "Name Cannot be null or Empty";
		}
		return repo.deleteContactByName(firstName);
	}
}
