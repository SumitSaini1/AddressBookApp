package com.addressbook.app.service;

import com.addressbook.app.model.Contact;
import com.addressbook.app.model.AddressBook;
import com.addressbook.app.repository.AddressBookDatabaseRepo;
import com.addressbook.app.repository.AddressBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.addressbook.app.dto.ContactDto;

@Service
public class AddressBookService {
	@Autowired
	private AddressBookDatabaseRepo repo;
	@Autowired
	private AddressBookRepository addressBookRepo;

	// method to create address book
	public String createAddressBook(String name) {
		if (addressBookRepo.findByName(name).isPresent()) {
			return "AddressBook by this " + name + " already exist";
		}
		AddressBook addressBook = new AddressBook();
		addressBook.setName(name);
		addressBookRepo.save(addressBook);
		return "AddressBook Create successfully name: " + name;

	}

	public String addContact(String bookName, ContactDto contactDTO) {

		AddressBook book = addressBookRepo.findByName(bookName)
				.orElseThrow(() -> new RuntimeException("Address Book by " + bookName + "not found"));
		Contact contact = new Contact();

		contact.setFirstName(contactDTO.getFirstName());
		contact.setLastName(contactDTO.getLastName());
		contact.setAddress(contactDTO.getAddress());
		contact.setCity(contactDTO.getCity());
		contact.setState(contactDTO.getState());
		contact.setZip(contactDTO.getZip());
		contact.setPhoneNumber(contactDTO.getPhoneNumber());
		contact.setEmail(contactDTO.getEmail());
		contact.setAddressBook(book);

		repo.save(contact);
		return "Successfully Contact Added";
	}

	public String updateByName(String firstName, ContactDto updatedContactDto) {

		Optional<Contact> optionalContact = repo.findByFirstName(firstName);

		if (optionalContact.isPresent()) {

			Contact contact = optionalContact.get();

			contact.setLastName(updatedContactDto.getLastName());
			contact.setAddress(updatedContactDto.getAddress());
			contact.setCity(updatedContactDto.getCity());
			contact.setState(updatedContactDto.getState());
			contact.setZip(updatedContactDto.getZip());
			contact.setPhoneNumber(updatedContactDto.getPhoneNumber());
			contact.setEmail(updatedContactDto.getEmail());

			repo.save(contact);

			return "Contact Updated Successfully";
		}

		return "Contact Not Found";
	}

	public List<Contact> showAllContacts() {
		return repo.findAll();
	}

	public String deleteContactById(Long id) {

		Contact contact = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Contact not found"));

		repo.delete(contact);

		return "Contact Deleted Successfully";
	}

}
