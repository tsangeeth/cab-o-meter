package com.sangeeth.cab.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	private static final Logger logger = Logger.getLogger(WebInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		logger.info("Starting cab-o-meter....");
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfiguration.class);
		
		servletContext.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.addMapping("/api/*");
		servlet.setLoadOnStartup(1);
	}

}