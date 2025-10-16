package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = {"http://localhost:3000"})
public class MemberController {

    @PostMapping("/signup")
    public boolean Signup(@RequestBody Member member) {
        boolean result = true;
        System.out.println(member.getId());
        System.out.println(member.getPwd());
        System.out.println(member.getName());
        System.out.println(member.getPhone());
        System.out.println(member.getEmail());
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
        boolean result = false; //아이디 존재O: 1, 존재X: 0
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";

        return msg;
    }
}
