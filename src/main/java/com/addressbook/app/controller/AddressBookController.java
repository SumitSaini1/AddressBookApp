package com.addressbook.app.controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.addressbook.app.model.AddressBook;
import com.addressbook.app.model.Contact;
import com.addressbook.app.service.AddressBookService;
import com.addressbook.app.dto.ContactDto;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
@RestController
@RequestMapping("/AddressBook")
public class AddressBookController {
	
	@Autowired
	private AddressBookService service;
	
	// create address book
	@PostMapping("/addBook/{bookName}")
	public String createBook(@PathVariable String bookName){
		return service.createAddressBook(bookName);


	}
	// create contact in address book
	@PostMapping("/addContact/{bookName}")
	public String addContact(@PathVariable String bookName,@RequestBody ContactDto contactDto) {
		return service.addContact(bookName,contactDto);
		
	}
	// edit contact by first Name
	@PutMapping("/edit/{firstName}")
	public String updateContactByName(@PathVariable String firstName, @RequestBody ContactDto contactdDto) {
		return service.updateByName(firstName,contactdDto);
	}

	// show all contacts
	@GetMapping("/showAll")
	public List<Contact> showContacts(){
		return service.showAllContacts();
	}

	@GetMapping("/showAllBook")
	public List<AddressBook> showAllAddressBook(){
		return service.showAllAddressBook();
	}
	@DeleteMapping("/delete/{id}")
	public String deleteContactByName(@PathVariable Long id) {
		return service.deleteContactById(id);
	}
	
	// show by city
	@GetMapping("/showCity/{firstName}/{city}")
	public List<Contact> showContacts(@PathVariable String firstName,@PathVariable String city){
		return service.findByCity(firstName,city);
	}
	// show by state
	@GetMapping("/showState/{firstName}/{state}")
	public List<Contact> showContactsByState(@PathVariable String firstName,@PathVariable String state){
		return service.findByState(firstName,state);
	}
	// group by city
	@GetMapping("/groupByCity")
	public Map<String,List<Contact>> groupBycity(){
		return service.groupByCity();
	}
	// group by State
	@GetMapping("/groupByState")
	public Map<String,List<Contact>> groupBystate(){
		return service.groupByState();
	}



}
