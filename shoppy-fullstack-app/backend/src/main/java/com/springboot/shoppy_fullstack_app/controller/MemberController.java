package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// RestController Annotation에 따라 Controller가 생성되면서 생성자도 함께 생성
// 이때 MemberServiceImpl 은 MemberService의 형태로 생성이 된다. => 컨테이너가 만들어준다.
// MemberServiceImpl은 구현체, MemberService는 인터페이스. Loosely Coupled 구조로 인해
// 인터페이스를 주입받고 구현체는 컨테이너에 의해 자동 주입된다. Spring의 DI 기능이다.
// Controller에 Service 주입하는 이유 : 비즈니스 로직을 서비스에서 처리하고, 컨트롤러는 요청/응답만 처리
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {

    //서비스 객체 호출
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService =  memberService;
    }

    @PostMapping("/signup")
    public boolean Signup(@RequestBody Member member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows == 1) result=true;
        return result;
    }

    @PostMapping("/login")
    public boolean Login(@RequestBody Member member) {
        boolean result = false;
        if(member.getId().equals("test") && member.getPwd().equals("1234"))
            result = true;

        return result;
    }

    @PostMapping("/idcheck")
    public String IdCheck(@RequestBody Member member) {
        boolean result = memberService.idCheck(member.getId());
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }
}
