package kr.gradle.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Web에서 나의 Local에 접속을 허용할때 설정파일
@Configuration
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


//    /images/**로 시작하는 URL이 웹 브라우저로부터 요청받으면, 이 요청은 실제 파일이 저장된 경로인 uploadPath로 리다이렉트 될 것입니다. 여기서 uploadPath는 당신의 속성 파일(예를 들어, application.properties 또는 application.yml)에서 설정한 값입니다.
//
// 예를 들어, 당신이 <img src="/images/item/f66a547f-d2aa-48b7-8d39-493a583f2c04.JPG" class="card-img-top" alt="ㅇㄴㅁㅇㅁㄴ" height="400"> 이렇게 이미지를 불러온다고 했을 때, 실제로 이 이미지는 ${uploadPath}/item/f66a547f-d2aa-48b7-8d39-493a583f2c04.JPG에 저장되어 있을 것입니다.
//
// 즉, uploadPath가 /home/user/uploads/라면, 실제 파일 경로는 /home/user/uploads/item/f66a547f-d2aa-48b7-8d39-493a583f2c04.JPG가 됩니다. 따라서 실제 이미지 파일은 웹 브라우저에 http://yourdomain.com/images/item/f66a547f-d2aa-48b7-8d39-493a583f2c04.JPG URL로 요청되고, 서버는 이 요청을 실제 파일 경로인 /home/user/uploads/item/f66a547f-d2aa-48b7-8d39-493a583f2c04.JPG로 리다이렉트해서 이미지 파일을 반환하게 됩니다.


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                //허용된 요청방식
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //허용될 요청 헤더
                .allowedHeaders("*")
                //인증에 관한 정보 허용
                .allowCredentials(true)
                //타임아웃 시간 설정
                .maxAge(3600);
    }
}