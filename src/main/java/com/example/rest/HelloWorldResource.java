package com.example.rest;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.service.HelloService;

@Path("/")
public class HelloWorldResource {

	@Inject
	private HelloService helloService;
	
	@GET
	@Path("sayhello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name") @Pattern(regexp="[a-zA-z ']*") String name) {
		return helloService.greetPerson(name);
	}
	
}
