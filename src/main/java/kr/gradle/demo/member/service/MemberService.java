package kr.gradle.demo.member.service;

import jakarta.persistence.EntityNotFoundException;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
//파이널로 올라온것 메모리로 올려보낸다.
//Test 단에서는 적으면 오류가 뜨니까주의
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

//     @Autowired -> 자동으로 메모리로 올라온다.
//    @Autowired
    private final MemberRepository memberRepository;

    public Member saverMember(Member member){
//      member 중복검사
        validateDulicate(member);
        return memberRepository.save(member);
    }

    private void validateDulicate(Member member) {
       Member findMember =  memberRepository.findByEmail(member.getEmail());

       if (findMember != null){
           //       만약 findMember가 있으면 예외를 던진다.
           throw new IllegalStateException("이미 등록된 사용자 입니다.");
       }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        없으면 새로만드는 문장.
//       Optional<Member> member =memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
          Member member =memberRepository.findByEmail(email);

//      Optional로 뽑는 이유 비었을때 메소드를
//        if (!member.isPresent()) {
//            throw new UsernameNotFoundException("해당 사용자가 없습니다."+email);
//        }
                if (member ==null) {
            throw new UsernameNotFoundException("해당 사용자가 없습니다."+email);
        }
//        Item.builder().id(1L).itemNm("ABC").build()

//        사용자 만듬
        log.info("==========================>"+member);
        return User.builder()
                .username(member.getName())
                .password(member.getPassowrd())
                .roles(member.getRole().toString())
                .build();
    }

}
