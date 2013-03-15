package com.sangeeth.cab.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeRepository;

@Controller
public class EmployeeController {
	
	@Autowired
	public EmployeeRepository employeeRepository;

	@RequestMapping(value = "/employees", method = RequestMethod.GET, produces="application/json")
	public List<Employee> search(WebRequest request){
		String name = request.getParameter("name");
		
		return employeeRepository.search(name);
		
		
		
	}
}
