package com.exmaple;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.persistence.PersistenceException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static com.example.util.Constants.INTERNAL_SYSTEM_ERROR;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.entity.Member;
import com.example.exception.ApplicationException;
import com.example.repository.MemberRepository;
import com.example.service.MemberService;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {
	
	@InjectMocks
	MemberService memberService;
	
	@Mock
	MemberRepository memberRepository;
	
	@Test(expected=ApplicationException.class)
	public void should_throw_an_application_error_when_insert_fails() throws ApplicationException {
		when(memberRepository.addMember(any(Member.class))).thenThrow(PersistenceException.class);
		memberService.addMember(new Member());
	}
	
	@Test
	public void should_throw_an_application_error_of_type_internal_system_exception_when_insert_fails() {
		try {
			when(memberRepository.addMember(any(Member.class))).thenThrow(PersistenceException.class);
			memberService.addMember(new Member());
		} catch(ApplicationException ae) {
			assertThat(ae.getCode(), is(500));
			assertThat(ae.getErrorMessage(), is(INTERNAL_SYSTEM_ERROR));
		}
	}
	
}
