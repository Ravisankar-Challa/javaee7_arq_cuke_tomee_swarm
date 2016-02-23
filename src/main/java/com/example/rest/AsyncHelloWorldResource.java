package com.example.rest;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.tomitribe.sabot.Config;

import com.example.service.HelloService;

@Path("/")
public class AsyncHelloWorldResource {
	
	private static final Logger LOG = Logger.getLogger(AsyncHelloWorldResource.class.getName());
	
	@Inject
	@Config("url")
	private String endpointUrl;
	
	@Resource
	private ManagedExecutorService mes;
	
	
	@GET
	@Path("hello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name") @Pattern(regexp="[a-zA-z ']*", message="Allows only characters") String name) {
		expensiveOperation();
		return String.format("url is : %s", endpointUrl);
	}
	
	@GET
	@Path("helloaysnc/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public void sayHelloAsync(final @Suspended AsyncResponse asyncResponse,
								@PathParam("name") 
								@Pattern(regexp="[a-zA-z ']*", message="Allows only characters") 
								String name) {
		mes.execute(() -> {
           expensiveOperation();
       });
	   asyncResponse.resume(String.format("url is : %s", endpointUrl));
	}

	public void expensiveOperation() {
		try {
			Thread.sleep(5000);
			LOG.info("I am done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
