package com.example.repository;

import static com.example.util.Constants.MEMBER_NOT_FOUND;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.entity.Member;
import com.example.exception.ApplicationException;

@Transactional
@ApplicationScoped
public class MemberRepository {

	private static final Logger LOG = Logger.getLogger(MemberRepository.class.getName());
	
	@PersistenceContext(unitName="test")
	private EntityManager em;
	
	public Member addMember(Member m) {
		em.persist(m);
		return m;
	}
	
	public void removeMember(Long id) throws ApplicationException {
		Member m1 = em.find(Member.class, id);
		if(m1 == null) {
			LOG.severe("Member with "+ id +" doesn't exists");
			throw new ApplicationException(404, MEMBER_NOT_FOUND);
		}
		else {
			em.remove(m1);
		}
	}
	
	public Member findMemberById(Long id) {
		return  em.find(Member.class, id);
	}
	
	public void updateMember(Member m) throws ApplicationException {
		Member m1 = em.find(Member.class, m.getId());
		if(m1 == null) {
			LOG.severe("Member with "+ m.getId() +" doesn't exists");
			throw new ApplicationException(404, MEMBER_NOT_FOUND);
		}
		m1.setAge(m.getAge());
		m1.setName(m.getName());
	}
	
	public List<Member> findAll() {
		return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
	}
	
}
