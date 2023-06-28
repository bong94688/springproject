package kr.gradle.demo.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import kr.gradle.demo.test.dto.TestDto;

@RestController
public class TestController {
    @GetMapping("/kr/gradle/demo/test")
    public TestDto hello(){

        TestDto testDto = new TestDto();
        testDto.setAge("10");
        testDto.setName("심봉교");

        return testDto;
    }

}
