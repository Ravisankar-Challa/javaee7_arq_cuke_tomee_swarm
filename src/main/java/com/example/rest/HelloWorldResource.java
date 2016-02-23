package com.example.rest;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tomitribe.sabot.Config;

import com.example.service.HelloService;

@Path("/")
public class HelloWorldResource {

	@Inject
	private HelloService helloService;
	
	@Inject
	@Config("name")
	private String env;
	
	@Inject
	@Config("url")
	private String endpointUrl;
	
	@GET
	@Path("sayhello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name") @Pattern(regexp="[a-zA-z ']*", message="Allows only characters") String name) {
		return String.format("%s and Environment is : %s and url is : %s",helloService.greetPerson(name), env, endpointUrl);
	}
	
}
