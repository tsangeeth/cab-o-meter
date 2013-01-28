package com.sangeeth.cab.configuration;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
@EnableWebMvc
@ComponentScan({"com.sangeeth.cab.web"})
public class WebConfiguration {
	
	@Bean
    public ViewResolver viewResolver()
    {
		return new ViewResolver() {
			public View resolveViewName(String viewName, Locale locale) throws Exception {
				MappingJackson2JsonView view = new MappingJackson2JsonView();
				ObjectMapper objectMapper = new ObjectMapper();
				view.setObjectMapper(objectMapper);
				objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
				view.setExtractValueFromSingleKeyModel(true);
				return view;
			}
		};
    }
	
	



}