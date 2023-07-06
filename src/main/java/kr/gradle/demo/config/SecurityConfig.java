package kr.gradle.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//설정 + websecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{

        System.out.println("1111111");

        return http.formLogin((formLogin)->{
//                    로그인페이지 설정
            formLogin.loginPage("/member/login");
//                    SpringSecurity에서 기본적으로 아이디의 userId
//                    비밀번호는 password로 사용
//                    어플리케이션에서 사용하는 아이디와 비밀번호 키값을
//                    username과 password로 매핑
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//                   로그인 요청 url 매핑(가로챌 url)
//                   매핑된 url 요청이 왔을 때 시큐리티에서 알아서 인증 처리
            formLogin.loginProcessingUrl("/member/login");
            formLogin.failureUrl("/member/login/error");
//                    로그인 실패 핸들러 등록
//            formLogin.failureHandler(loginFailureHandler);
//                    로그인 성공 시 띄워줄 페이지 url
            formLogin.defaultSuccessUrl("/");
        }).
                logout((logout)->{
//                   로그아웃 url 지정
                    logout.logoutUrl("/member/logout");
//                    사용자 인증정보가 저장된 시큐리티 컨텍스트가
//                    세션에 저장되기 때문에 세션을 만료시켜서
//                    시큐리티 컨텍스트를 제거한다.
                    logout.invalidateHttpSession(true);
//                    로그아웃 성공시 호출 할 요청 지정
                    logout.logoutSuccessUrl("/");
                })
                .build();
//               return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){


//        BCryptPasswordEncoder 단방향으로 암호화해주는 객체
        return new BCryptPasswordEncoder();
    }




}
