package com.sangeeth.cab.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.sangeeth.cab.employee.Employee;
import com.sangeeth.cab.employee.EmployeeRepository;
import com.sangeeth.cab.requests.CabRequestRepository;

public class CabRequestController {

	@Autowired
	public CabRequestRepository repository;

	@RequestMapping(value = "/cabrequest", method = RequestMethod.POST, produces="application/json")
	public void create(WebRequest request){
	}
}
