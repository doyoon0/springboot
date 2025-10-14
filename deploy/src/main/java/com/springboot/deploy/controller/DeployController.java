package com.springboot.deploy.controller;

import com.springboot.deploy.dto.Deploy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeployController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(Deploy deploy, Model model) {
        String result = "";
        if(deploy.getId().equals("test") && deploy.getPass().equals("1234")) result = "로그인 성공";
        else result = "로그인 실패";

        model.addAttribute("result", result);
        return "loginResult";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(Deploy deploy, Model model) {
        model.addAttribute("deploy", deploy);

        return "signupResult";
    }
}
