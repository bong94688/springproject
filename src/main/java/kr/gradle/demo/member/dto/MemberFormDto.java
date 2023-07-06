package kr.gradle.demo.member.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberFormDto {


//   빈칸 허용하지않음
    @NotBlank(message = "이름은 필수 항목 입니다.")
    private  String name;
    @NotBlank(message = "이메일은 필수 항목 입니다.")
    @Email(message = "이메일 형식으로 입력하세요.")
    private  String email;

    @NotBlank(message = "비밀번호는 필수 항목 입니다.")
//  길이 제한 어노테이션
    @Length(min = 4,max = 12,message = "최소4자 최대12자를 입력하세요.")
    private  String password;

    @NotBlank(message = "주소는 필수항목 입니다.")
    private  String address;
}
