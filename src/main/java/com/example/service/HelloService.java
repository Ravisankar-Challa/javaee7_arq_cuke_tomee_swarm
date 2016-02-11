package com.example.service;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloService {

	public String greetPerson(String name) {
		return "Hello "+name+" now the time is "+LocalDate.now();
	}
	
}
