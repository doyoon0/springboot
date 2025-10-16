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
}
