package kr.gradle.demo.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {


//       그냥 정해진것!! 이해하고 게속 재사용
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

         String userId="";
         if(authentication != null){
             userId = authentication.getName();
         }



        return Optional.of(userId);
    }
}
