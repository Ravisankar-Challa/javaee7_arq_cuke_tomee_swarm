package com.example.service;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.example.entity.Member;
import com.example.repository.MemberRepository;

@Startup
@Singleton(name="SomeRandonName")
public class AppStartupService {
	
	private static final Logger LOG = Logger.getLogger(AppStartupService.class.getName());

	@Inject
	private MemberRepository memberRepo;
	
	@PostConstruct
	public void init() {
		Member m1 = new Member();
		m1.setName("Test1");
		m1.setAge(20);
		memberRepo.addMember(m1);
		
		Member m2 = new Member();
		m2.setName("Test2");
		m2.setAge(25);
		memberRepo.addMember(m2);
		
		memberRepo.findAll().forEach(System.out::println);
	}
	
}
