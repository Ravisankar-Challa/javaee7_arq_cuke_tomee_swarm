package com.example;

import com.example.entity.Member;

public class MemberBuilder {
	
	private final Member member = new Member();
	
	public static MemberBuilder newMember() {
		return new MemberBuilder();
	}
	
	public MemberBuilder id(Long id) {
		this.member.setId(id);
		return this;
	}
	
	public MemberBuilder name(String name) {
		this.member.setName(name);
		return this;
	}
	
	public MemberBuilder age(int age) {
		this.member.setAge(age);
		return this;
	}
	
	public Member build() {
		return  this.member;
	}
}
