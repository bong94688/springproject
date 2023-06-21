package test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.dto.TestDto;

@RestController
public class TestController {

    @GetMapping("/test")
    public TestDto hello(){

        TestDto testDto = new TestDto();
        testDto.setAge("10");
        testDto.setName("심봉교");

        return testDto;
    }

}
