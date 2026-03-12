package com.addressbook.app.controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.addressbook.app.model.Contact;
import com.addressbook.app.service.AddressBookService;
import com.addressbook.app.dto.ContactDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/AddressBook")
public class AddressBookController {
	
	@Autowired
	private AddressBookService service;
	
	@PostMapping("/addContact")
	public String addContact(@RequestBody ContactDto contactDto) {
		return service.addContact(contactDto);
		
	}
	@PutMapping("/edit/{firstName}")
	public String updateContactByName(@PathVariable String firstName, @RequestBody ContactDto contactdDto) {
		return service.updateByName(firstName,contactdDto);
	}
	@GetMapping("showAll")
	public List<Contact> showContacts(){
		return service.showAllContacts();
	}
	@DeleteMapping("/delete/{firstName}")
	public String deleteContactByName(@PathVariable String firstName) {
		return service.deleteContactByName(firstName);
	}

}
