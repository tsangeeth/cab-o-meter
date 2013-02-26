package com.sangeeth.cab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sangeeth.cab.contract.Address;
import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.web.authentication.Authentication;
import com.sangeeth.cab.web.authentication.IAutheticationStore;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IAutheticationStore authenticationStore;
	
	@RequestMapping(value = "", method = RequestMethod.GET, produces="application/json")
	public User getUser(){
		Authentication authentication = authenticationStore.get();
		
		return authentication.user();
//		Address address = new Address("No.10, 12th Avenue", "Madagacar Street", "Kingston Cross", "Fantasy City", "Utopia","near Atlantis");
//		return new User("JB442X", "John", "Doe","Arumugam", "admin", "administration", address);
	}
	
	
}
