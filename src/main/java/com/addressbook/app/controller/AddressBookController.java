package com.addressbook.app.controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.addressbook.app.model.Contact;
import com.addressbook.app.service.AddressBookService;
import com.addressbook.app.dto.ContactDto;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/AddressBook")
public class AddressBookController {
	
	@Autowired
	private AddressBookService service;
	
	@PostMapping("/addBook/{bookName}")
	public String createBook(@PathVariable String bookName){
		return service.createAddressBook(bookName);


	}
	@PostMapping("/addContact/{bookName}")
	public String addContact(@PathVariable String bookName,@RequestBody ContactDto contactDto) {
		return service.addContact(bookName,contactDto);
		
	}
	@PutMapping("/edit/{firstName}")
	public String updateContactByName(@PathVariable String firstName, @RequestBody ContactDto contactdDto) {
		return service.updateByName(firstName,contactdDto);
	}
	@GetMapping("/showAll")
	public List<Contact> showContacts(){
		return service.showAllContacts();
	}
	@DeleteMapping("/delete/{id}")
	public String deleteContactByName(@PathVariable Long id) {
		return service.deleteContactById(id);
	}

}
