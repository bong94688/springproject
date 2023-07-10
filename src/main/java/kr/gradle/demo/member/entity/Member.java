package kr.gradle.demo.member.entity;


import jakarta.persistence.*;
import kr.gradle.demo.member.constant.Role;
import kr.gradle.demo.member.dto.MemberFormDto;
import kr.gradle.demo.utils.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {


    @Id
//   오토 인클리티드 할수있게만든다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long Id;

    private  String name;

    @Column(unique = true)
    private  String email;

    private  String passowrd;

    private  String adress;

//  EnumType
//ROLE ->  enum에 User,ADMIN 값이 설정되면, 데이터베이스에는 실제 문자열 'USER' 또는 'ADMIN' 저장.
    @Enumerated(EnumType.STRING)
    private Role role;

    public static  Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAdress(memberFormDto.getAddress());
        member.setRole(Role.User);
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassowrd(password);
        return member;
    }
}





