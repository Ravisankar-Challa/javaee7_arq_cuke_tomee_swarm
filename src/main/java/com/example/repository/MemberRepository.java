package com.example.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.entity.Member;

@Transactional
public class MemberRepository {

	@PersistenceContext(unitName="test")
	private EntityManager em;
	
	public Member addMember(Member m) {
		em.persist(m);
		return m;
	}
	
	public void removeMember(Long id) {
		em.remove(em.find(Member.class, id));
	}
	
	public Member findMember(Long id) {
		return em.find(Member.class, id);
	}
	
	public void updateMember(Member m) {
		em.merge(em.find(Member.class, m.getId()));
	}
	
	public List<Member> findAll() {
		return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
	}
	
}