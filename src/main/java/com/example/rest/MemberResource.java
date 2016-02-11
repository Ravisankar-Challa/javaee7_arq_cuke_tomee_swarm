package com.example.rest;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.constraints.Pattern;
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
import com.example.exception.ApplicationException;
import com.example.service.MemberService;

@Path("/")
public class MemberResource {
	
	private static final Logger LOG = Logger.getLogger(MemberResource.class.getName());

	@Inject
	private MemberService memberService;
	
	@GET
	@Path("member/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Member findMember(@PathParam("id") @Pattern(regexp="[0-9]*", message="Member Id Should Be Number") String id) throws ApplicationException {
		return memberService.findMember(Long.valueOf(id));
	}
	
	@POST
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMember(@Context UriInfo uriInfo, Member member) throws ApplicationException {
		Member 	m = memberService.addMember(member);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(m.getId())).build()).build();
	}
	
	@PUT
	@Path("member")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMember(Member member) throws ApplicationException {
		memberService.updateMember(member);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("member/{id}")
	public Response removeMember(@PathParam("id") Long id) throws ApplicationException {
		memberService.removeMember(id);
		return Response.noContent().build();
	}
	
	@GET
	@Path("members")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> findAll() {
		return memberService.findAll();
	}
}
