package kr.gradle.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Web에서 나의 Local에 접속을 허용할때 설정파일
public class WebMvcConfig  implements WebMvcConfigurer {

//    uploadPath 라는것을 가져온다.
    @Value(value = "${uploadPath}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//       Resource
//"/images/**" 여기에 웹에서 접근하면 진짜 경로로 보내준다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
//        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
