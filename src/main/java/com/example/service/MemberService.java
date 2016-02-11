package com.example.service;

import static com.example.util.Constants.INTERNAL_SYSTEM_ERROR;
import static com.example.util.Constants.INVALID_INPUT;
import static com.example.util.Constants.MEMBER_NOT_FOUND;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import com.example.entity.Member;
import com.example.exception.ApplicationException;
import com.example.repository.MemberRepository;

@ApplicationScoped
public class MemberService {
	
	private static final Logger LOG = Logger.getLogger(MemberService.class.getName());
	
	@Inject
	private MemberRepository memberRepo;
	
	public Member addMember(Member m) throws ApplicationException {
		try {
			return memberRepo.addMember(m);
		} 
		catch(Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			if(e.getCause() !=null && e.getCause().getCause() !=null 
					&& e.getCause().getCause() instanceof ConstraintViolationException) {
				throw new ApplicationException(400, INVALID_INPUT);
			}
			else {
				throw new ApplicationException(500, INTERNAL_SYSTEM_ERROR);
			}
		}
	}
	
	public void removeMember(Long id) throws ApplicationException {
		try {
			memberRepo.removeMember(id);
		}catch(Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			if(e.getCause().getCause() instanceof ConstraintViolationException) {
				throw new ApplicationException(400, INVALID_INPUT);
			}
			else {
				throw new ApplicationException(500, INTERNAL_SYSTEM_ERROR);
			}
		}
	}
	
	public Member findMember(Long id) throws ApplicationException {
		Member m = memberRepo.findMemberById(id);
		if(m == null) {
			LOG.severe("Member with "+ id +" doesn't exists");
			throw new ApplicationException(404, MEMBER_NOT_FOUND);
		}
		return m;
	}
	
	public void updateMember(Member m) throws ApplicationException {
		try {
			memberRepo.updateMember(m);
		}catch(Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			if(e.getCause() !=null && e.getCause().getCause() !=null 
					&& e.getCause().getCause() instanceof ConstraintViolationException) {
				throw new ApplicationException(400, INVALID_INPUT);
			}
			else {
				throw new ApplicationException(500, INTERNAL_SYSTEM_ERROR);
			}
		}
	}
	
	public List<Member> findAll() {
		return memberRepo.findAll();
	}
}
