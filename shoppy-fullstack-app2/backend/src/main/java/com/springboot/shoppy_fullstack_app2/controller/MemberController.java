package com.springboot.shoppy_fullstack_app2.controller;

import com.springboot.shoppy_fullstack_app2.dto.Member;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    @PostMapping("/login")
    public boolean Login(@RequestBody Member member) {
        boolean result = false;
        if(member.getId().equals("test") && member.getPwd().equals("1234"))
            result = true;

        return result;
    }

    @PostMapping("/signup")
    public boolean SignUp(@RequestBody Member member) {
        boolean result = true;
        return result;
    }

    @PostMapping("/idcheck")
    public String IdCheck(@RequestBody Member member) { //나중에 아이디뿐 아니라 이메일이나 닉네임 중복체크도 해야 한다든가
        boolean result = false;
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }

}
