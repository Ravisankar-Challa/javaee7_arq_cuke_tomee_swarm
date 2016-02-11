package com.example.rest;

import static com.example.util.Constants.INVALID_INPUT;
import static com.example.util.Constants.MEMBER_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.entity.Member;

@RunWith(Arquillian.class)
public class MemberResourceIT {

	@Deployment
	public static Archive<WebArchive> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "membertest.war")
						 .addPackages(true, "com.example")
						 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
						 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
	}
	
	@ArquillianResource
	URL baseURL;
	
	@Test
	@InSequence(1)
	public void testFindMemberById() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
								  .path("api/member/{id}")
								  .resolveTemplate("id", 1)
								  .request()
								  .accept(MediaType.APPLICATION_JSON)
								  .get();
		assertEquals(200, response.getStatus());
		Member member = response.readEntity(Member.class);
		assertEquals("Test1", member.getName());
		assertEquals(20, member.getAge());
		
	}
	
	@Test
	@InSequence(2)
	public void testAddMember() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
								  .path("api/member")
								  .request()
								  .post(Entity.entity(" {" + 
													  		"      \"name\" : \"Steven\"," + 
													  		"	   \"age\" : 68" + 
													  		"    }", 
											MediaType.APPLICATION_JSON));
		assertEquals(201, response.getStatus());
		assertTrue(response.getHeaderString("Location").contains("/api/member/"));
	}
	
	@Test
	@InSequence(3)
	public void testUpdateMember() {
		Response response =ClientBuilder.newClient().target(baseURL.toString())
								  .path("api/member")
								  .request()
								  .put(Entity.entity(" {" + 
													  		"      \"id\": 2," + 
													  		"      \"name\" : \"Scott\"," + 
													  		"	  \"age\" : 30" + 
													  		"    }", 
											MediaType.APPLICATION_JSON));
		assertEquals(204, response.getStatus());
		
		Member m = ClientBuilder.newClient().target(baseURL.toString())
								.path("api/member/2")
								.request()
								.accept(MediaType.APPLICATION_JSON)
								.get(Member.class);
		assertEquals(30, m.getAge());
		assertEquals("Scott", m.getName());
	}
	
	@Test
	@InSequence(4)
	public void testRemoveMember() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
				  .path("api/member/{id}")
				  .resolveTemplate("id", 2)
				  .request()
				  .delete();
		assertEquals(204, response.getStatus());
	}
	
	@Test
	@InSequence(5)
	public void testFindMemberByUnknownId() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
				  .path("api/member/{id}")
				  .resolveTemplate("id", 2)
				  .request()
				  .accept(MediaType.APPLICATION_JSON)
				  .get();
		assertEquals(404, response.getStatus());
		Map<String, Object> errorResponse = response.readEntity(new GenericType<Map<String, Object>>(){});
		assertEquals(404, errorResponse.get("code"));
		assertEquals(MEMBER_NOT_FOUND, errorResponse.get("message"));
	}
	
	@Test
	@InSequence(6)
	public void testFindAll() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
								  .path("api/members")
								  .request()
								  .accept(MediaType.APPLICATION_JSON)
								  .get();
		assertEquals(200, response.getStatus());
		List<Member> members = response.readEntity(new GenericType<ArrayList<Member>>(){});
		assertEquals(2, members.size());
		
	}
	
	@Test
	@InSequence(7)
	public void should_throw_invalid_input_error_when_find_member_call_has_characters() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
										  .path("api/member/INVALIDINPUT")
										  .request()
										  .accept(MediaType.APPLICATION_JSON)
										  .get();
		assertEquals(400, response.getStatus());		
	}
	
	@Test
	@InSequence(8)
	public void should_throw_invalid_input_error_when_a_member_with_name_more_than_32_characters_is_add() {
		Response response = ClientBuilder.newClient().target(baseURL.toString())
				  .path("api/member")
				  .request()
				  .post(Entity.entity(" {" + 
									  		"      \"name\" : \"Steven111111111111111111111111133\"," + 
									  		"	   \"age\" : 68" + 
									  		"    }", 
							MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
		assertEquals("application/error+json", response.getHeaderString("Content-Type"));
		Map<String, Object> errorResponse = response.readEntity(new GenericType<Map<String, Object>>(){});
		assertEquals(400, errorResponse.get("code"));
		assertEquals(INVALID_INPUT, errorResponse.get("message"));
		
	}
		
}
