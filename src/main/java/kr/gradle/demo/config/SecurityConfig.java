package kr.gradle.demo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;


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
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"));
////                   로그아웃 url 지정
//                    logout.logoutUrl("/member/logout");
////                    사용자 인증정보가 저장된 시큐리티 컨텍스트가
////                    세션에 저장되기 때문에 세션을 만료시켜서
////                    시큐리티 컨텍스트를 제거한다.
//                    logout.invalidateHttpSession(true);
////                    로그아웃 성공시 호출 할 요청 지정
                    logout.logoutSuccessUrl("/");
                }) .authorizeHttpRequests((author)-> {
                    author.requestMatchers("/css/**").permitAll();
                    author.requestMatchers("/js/**").permitAll();
                    author.requestMatchers("/upload/**").permitAll();
                    author.requestMatchers("/images/**").permitAll();
                    author.requestMatchers("/fragments/**").permitAll();
                    author.requestMatchers("/layouts/**").permitAll();
//                    author.requestMatchers("/item/**").permitAll();
                    author.requestMatchers("/").permitAll();
                    author.requestMatchers("/member/**","/item/**").permitAll();
                    author.requestMatchers("/board/**").hasAnyRole("ADMIN","User");
                    author.requestMatchers("/admin/**").hasAnyRole("ADMIN");
                    author.requestMatchers("/member/join").permitAll();
                    author.requestMatchers("/member/login").permitAll();
                    author.requestMatchers("/meber/idCheck").permitAll();
                    author.requestMatchers("/api/**").permitAll();
                    author.anyRequest().authenticated();
                })
                .exceptionHandling((exception)->{
                  exception.authenticationEntryPoint(new AuthenticationEntryPoint() {
                      @Override
                      public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                          response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
                      }
                  }) ;
                })


//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorizaRequests) ->{
//                    authorizaRequests.requestMatchers("/").permitAll();
////                            css ,js, images, upload 같은 정적 리소스들도 권한처리 필수
//                    authorizaRequests.requestMatchers("/css/**").permitAll();
//                    authorizaRequests.requestMatchers("/js/**").permitAll();
//                    authorizaRequests.requestMatchers("/upload/**").permitAll();
//                    authorizaRequests.requestMatchers("/images/**").permitAll();
////                    authorizaRequests.requestMatchers(("/members/**", "/item/**", "/images/**").permitAll();
//                    //                    게시판 기능은 권한을 가지고 있는 사용자만 사용가능
//                    authorizaRequests.requestMatchers("/board/**").hasAnyRole("ADMIN","USER");
////                    관리자 페이지는 관리자만 사용가능
//                    authorizaRequests.requestMatchers("/admin/**").hasAnyRole("ADMIN");
////                      회원가입 , 로그인 ,아이디 중복체크 등 요청은 모든 사용자가 사용가능
//                    authorizaRequests.requestMatchers("/member/join").permitAll();
//                    authorizaRequests.requestMatchers("/member/login").permitAll();
//                    authorizaRequests.requestMatchers("/member/join").permitAll();
//                    authorizaRequests.requestMatchers("/member/idCheck").permitAll();
//                    authorizaRequests.requestMatchers("/api/**").permitAll();
//                    authorizaRequests.anyRequest().authenticated();
//                })
                .getOrBuild();
//               return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){


//        BCryptPasswordEncoder 단방향으로 암호화해주는 객체
        return new BCryptPasswordEncoder();
    }




}
