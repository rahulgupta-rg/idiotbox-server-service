package com.idiotBoxServer.util;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Appconfig {

	@Bean
	public FilterRegistrationBean<CORSResponseFilter> filterRegistrationBean() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		FilterRegistrationBean<CORSResponseFilter> registrationBean = new FilterRegistrationBean();
		CORSResponseFilter customURLFilter = new CORSResponseFilter();
		registrationBean.setFilter(customURLFilter);
		registrationBean.setOrder(2); // set precedence
		return registrationBean;
	}
}