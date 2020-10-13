package com.idiotBoxServer.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CORSResponseFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		HttpServletResponse res= (HttpServletResponse) response;
		System.out.println("Intercepted by filter"+req.getRequestedSessionId()+"   with uri "+ req.getRequestURL());
		 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
		
		 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Credentials", "true");
		 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
		 ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		 
		chain.doFilter(req, response);
		System.out.println("response attrib : "+ res.getContentType());
	}
}