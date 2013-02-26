package com.sangeeth.cab.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.stub;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.ILoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sangeeth.cab.configuration.WebConfiguration;
import com.sangeeth.cab.contract.Address;
import com.sangeeth.cab.contract.User;
import com.sangeeth.cab.web.authentication.AutoLoginInterceptor;
import com.sangeeth.cab.web.authentication.IAutheticationStore;
import com.sangeeth.cab.web.configuration.TestWebConfiguration;



import static java.util.Arrays.*;



//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, TestWebConfiguration.class})
public class UserControllerTest  {
	
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private AutoLoginInterceptor loginInterceptor;


	private MockMvc mvc;

	private User defaultUser;
	
	
	@Before
	public void setup() {
		this.mvc = MockMvcBuilders.webApplicationContextSetup(context).build();
		Address address = new Address("No.10, 12th Avenue", "Madagacar Street", "Kingston Cross", "Fantasy City", "Utopia","near Atlantis");
		defaultUser = new User("JB442X", "John", "Doe","Arumugam", "admin", "administration", address);
	}

	@Test
	public void shouldGiveInformationAboutEmployee() throws Exception {
		// Arrange
		loginInterceptor.signIn(defaultUser);
		
		// Act
		MediaType jsonType = MediaType.APPLICATION_JSON;
		
		mvc.perform(get("/user").accept(jsonType))
		//Assert
			.andExpect(status().isOk())
			.andExpect(content().mimeType(jsonType)) 
			.andExpect(jsonPath("$.employeeId").value("JB442X"))
			.andExpect(jsonPath("$.firstName").value("John"))
			.andExpect(jsonPath("$.lastName").value("Doe"))
			.andExpect(jsonPath("$.middleName").value("Arumugam"))
			.andExpect(jsonPath("$.role").value("admin"))
			.andExpect(jsonPath("$.costCentre").value("administration"))
			.andExpect(jsonPath("$.address.line1").value("No.10, 12th Avenue"))
			.andExpect(jsonPath("$.address.line2").value("Madagacar Street"))
			.andExpect(jsonPath("$.address.locality").value("Kingston Cross"))
			.andExpect(jsonPath("$.address.city").value("Fantasy City"))
			.andExpect(jsonPath("$.address.state").value("Utopia"))
			.andExpect(jsonPath("$.address.landmark").value("near Atlantis"));
	}
	


}
