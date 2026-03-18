package com.addressbook.app.service;

import com.addressbook.app.model.Contact;
import com.addressbook.app.model.AddressBook;
import com.addressbook.app.repository.AddressBookDatabaseRepo;
import com.addressbook.app.repository.AddressBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.addressbook.app.dto.ContactDto;
import java.util.stream.*;
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


		boolean exist=repo.existsByFirstNameAndLastName(contact.getFirstName(),contact.getLastName() );
		if(exist){
			return "Contact Already Exist in "+bookName;
		}
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
	// show address books
	public List<AddressBook> showAllAddressBook(){
		return addressBookRepo.findAll();
	}
	// delete contact by id 
	public String deleteContactById(Long id) {

		Contact contact = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Contact not found"));

		repo.delete(contact);

		return "Contact Deleted Successfully";
	}

	

	// dictionary by city
	public Map<String,List<Contact>> groupByCity(){
		return repo.findAll().stream().collect(Collectors.groupingBy(Contact::getCity));

	}
	// dictionary by state
	public Map<String,List<Contact>> groupByState(){
		return repo.findAll().stream().collect(Collectors.groupingBy(Contact::getState));

	}

	// search by city
	public List<Contact> findByCity(String firstName,String city){
		
		return repo.findAll().stream().filter(c-> c.getCity().equalsIgnoreCase(city) && c.getFirstName().equalsIgnoreCase(firstName)).toList();
	}
	// search by State 
	public List<Contact> findByState(String firstName,String state){
		List<Contact> contacts=repo.findAll().stream().filter(c-> c.getState().equalsIgnoreCase(state)&& c.getFirstName().equalsIgnoreCase(firstName)).toList();
		return contacts;
	}


}
