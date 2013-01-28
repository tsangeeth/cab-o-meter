package com.sangeeth.cab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sangeeth.cab.contract.Address;
import com.sangeeth.cab.contract.UserInformation;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, produces="application/json")
	public UserInformation getUser(@PathVariable String employeeId){
		Address address = new Address("No.10, 12th Avenue", "Madagacar Street", "Kingston Cross", "Fantasy City", "Utopia","near Atlantis");
		return new UserInformation("JB442X", "John", "Doe","Arumugam", "admin", "administration", address);
	}
	
}
