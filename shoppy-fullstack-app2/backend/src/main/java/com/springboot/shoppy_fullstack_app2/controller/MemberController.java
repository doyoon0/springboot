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

}
