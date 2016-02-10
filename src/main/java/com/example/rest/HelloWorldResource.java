package com.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorldResource {

	@GET
	@Path("sayhello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World!!!";
	}
	
}
