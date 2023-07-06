package kr.gradle.demo.member.controller;

import jakarta.validation.Valid;
import kr.gradle.demo.member.dto.MemberFormDto;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping(value = "/member")
public class MemberController {


    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model){

//      객체 하나를 넘겨서 뷰에서 받아서 메핑을 한다. th:field에서 name 대신 쓰고 정보를 앃고 다시 post로 돌아간다.
        model.addAttribute("memberFormDto",new MemberFormDto());

        return "member/memberFrom";
    }
    @PostMapping("/new")
//    @Valid -> @Validation 을 자동으로 체크 한다. DTo
//    bindingResult 바인딩 결과 받아오고, => 잘못된 경우 Model에 에러메시지 보냄
    public String memberForm(@Valid MemberFormDto memberFormDto
    ,BindingResult bindingResult,Model model){
        log.info("================>"+memberFormDto);
        if(bindingResult.hasErrors()){
//            에러가 있으면 입력창으로 돌려줌
            return "member/memberFrom";
        }
        try {
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.saverMember(member);
        }catch (IllegalStateException illegalStateException){
            model.addAttribute("errorMessage",illegalStateException.getMessage());
            return "member/memberFrom";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String Login(){
        return "member/memberLogin";
    }
    @GetMapping("/login/error")
    public String Loginerror(){

        return"member/memberLogin";
    }

//    @PostMapping("/login")
//    public

}
