package com.example.service;

import static com.example.util.Constants.INVALID_INPUT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.MemberBuilder;
import com.example.entity.Member;
import com.example.exception.ApplicationException;
import com.example.exception.ApplicationExceptionMapper;
import com.example.repository.MemberRepository;
import com.example.util.Constants;

@RunWith(Arquillian.class)
public class MemberServiceIT {

	@Deployment
	public static Archive<WebArchive> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "membertest.war")
						 .addClasses(MemberRepository.class, Member.class, MemberBuilder.class, Constants.class, 
								 ApplicationException.class, ApplicationExceptionMapper.class, MemberService.class)
						 .addAsWebInfResource("beans.xml", "beans.xml")
						 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
	}

	@Inject
	MemberService memberService;
	
	@Test
	@UsingDataSet("datasets/initial-members.yml")
	@ShouldMatchDataSet(value="datasets/add-expected-members.yml", excludeColumns={"id"})
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void testAddMember() throws ApplicationException {
		Member member = MemberBuilder.newMember()
									 .age(68)
									 .name("Steven")
									 .build();
		memberService.addMember(member);
	}
	
	@Test
	@UsingDataSet("datasets/initial-members.yml")
	@ShouldMatchDataSet(value="datasets/update-expected-members.yml", excludeColumns={"id"})
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void testUpdateMember() throws ApplicationException {
		memberService.updateMember(MemberBuilder.newMember()
					                             .id(new Long(100))
												 .age(21)
												 .name("Scott")
												 .build());
	}
	
	@Test
	@UsingDataSet("datasets/initial-members.yml")
	@ShouldMatchDataSet(value="datasets/remove-expected-members.yml", excludeColumns={"id"})
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void testDeleteMember() throws ApplicationException {
		memberService.removeMember(new Long(101));	
	}
	
	@Test
	@UsingDataSet("datasets/initial-members.yml")
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void testFindMemberById() throws ApplicationException {
		Member member = memberService.findMember(new Long(100));	
		assertEquals(20, member.getAge());
		assertEquals("Test1",member.getName());
	}
	
	@Test(expected=	ApplicationException.class)
	@UsingDataSet("datasets/initial-members.yml")
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void should_throw_application_exception_when_member_not_found() throws ApplicationException {
		memberService.findMember(new Long(200));	
	}
	
	@Test
	@UsingDataSet("datasets/initial-members.yml")
	@Cleanup(strategy = CleanupStrategy.USED_TABLES_ONLY, phase = TestExecutionPhase.AFTER)
	public void testFindAllMembers() {
		assertEquals(2, memberService.findAll().size());
	}
	
	@Test
	public void should_throw_invalid_input_exception() throws ApplicationException {
		try {
			memberService.addMember(MemberBuilder.newMember()
												 .age(68)
												 .name("Steven123456789123456781234567833")
												 .build());
		} catch(ApplicationException e) {
			assertThat(e.getCode(), equalTo(400));
			assertThat(e.getErrorMessage(), equalTo(INVALID_INPUT));
		}
	}
	
	
}
