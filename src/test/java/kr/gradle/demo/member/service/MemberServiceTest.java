package kr.gradle.demo.member.service;

import kr.gradle.demo.member.dto.MemberFormDto;
import kr.gradle.demo.member.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
//Test가 끝나면 트랜잭션이 일어나 롤백한다
//@Rollback(value = false)
class MemberServiceTest {


    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;



    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAdress("서울시 마포구 합정동");
        memberFormDto.setPassowrd("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member savedMember = memberService.saverMember(member);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAdress(), savedMember.getAdress());
        assertEquals(member.getPassowrd(), savedMember.getPassowrd());
        assertEquals(member.getRole(), savedMember.getRole());

    }

}