package com.blogapp.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configblog {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
