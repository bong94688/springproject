package kr.gradle.demo.member.repository;

import kr.gradle.demo.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {


//    Member형태의 리턴을 Email로 뽑아온다
   Member findByEmail(String email);

}
