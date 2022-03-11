package com.ronit.filters;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ronit.utils.TokenManager;

@WebFilter({"/admin/customer/*", "/admin/company/*"})
public class AuthorizationFilter implements Filter{
	
	@Autowired
	private TokenManager tokenManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		boolean isAuthorized = tokenManager.isTokenExists(httpServletRequest.getHeader("authorization"));
		if (isAuthorized) {
			chain.doFilter(request, response);
//			return;
		}else {
			httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
		}
	}
	
	
}

	

