package com.sangeeth.cab.web.authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sangeeth.cab.contract.User;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private IAutheticationStore authenticationStore;
	
	private User user;

	public void signIn(User user){
		this.user = user;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		authenticationStore.store(new Authentication(user));
		return true;
	}
}
