package com.example.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.example.entity.Member;
import com.example.repository.MemberRepository;

@Path("/")
public class MemberResource {
	
	private static final Logger LOG = Logger.getLogger(MemberResource.class.getName());

	@Inject
	private MemberRepository memberRepo;
	
	@GET
	@Path("member/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member findMember(@PathParam("id") Long id) {
		return memberRepo.findMember(id);
	}
	
	@POST
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMember(@Context UriInfo uriInfo, Member member) {
		Member m = memberRepo.addMember(member);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(m.getId())).build("")).build();
	}
	
	@PUT
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMember(Member member) {
		memberRepo.updateMember(member);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("member/{id}")
	public Response removeMember(@PathParam("id") Long id) {
		memberRepo.removeMember(id);
		return Response.noContent().build();
	}
	
	@GET
	@Path("members")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> findAll() {
		return memberRepo.findAll();
	}
}
