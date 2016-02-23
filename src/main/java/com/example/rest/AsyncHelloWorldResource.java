package com.example.rest;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.tomitribe.sabot.Config;

@Path("/")
public class AsyncHelloWorldResource {
	
	private static final Logger LOG = Logger.getLogger(AsyncHelloWorldResource.class.getName());
	
	@Inject
	@Config("url")
	private String endpointUrl;
	
	@Resource
	private ManagedExecutorService mes;
	
	private static Client client = ClientBuilder.newClient();
	
	@GET
	@Path("hello/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello(@PathParam("name") @Pattern(regexp="[a-zA-z ']*", message="Allows only characters") String name) {
		return client.target(endpointUrl).request().get(String.class);
	}
	
	@GET
	@Path("helloasync/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public void sayHelloAsync(final @Suspended AsyncResponse asyncResponse,
								@PathParam("name") 
								@Pattern(regexp="[a-zA-z ']*", message="Allows only characters") 
								String name) {
		mes.submit(() -> {
			client.target(endpointUrl).request().async().get(new InvocationCallback<String>() {

				@Override
				public void completed(String response) {
					asyncResponse.resume(response);
				}

				@Override
				public void failed(Throwable throwable) {
					
				}
			});
       });
	}
}
