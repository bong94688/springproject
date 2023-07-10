package kr.gradle.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {


//    AuditorAware<String> 객체가 필요하고 그 객체를 AuditorAwareImpl형태로 리턴해준다.
    @Bean
    public AuditorAware<String> auditorProvider(){

        return new AuditorAwareImpl();
    }

}
