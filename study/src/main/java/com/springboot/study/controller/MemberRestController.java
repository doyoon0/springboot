package com.springboot.study.controller;

import com.springboot.study.dto.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController //rest api를 위한 mapping이 되어짐. 내부에 @ResponseBody 포함, Map 객체 생성 없이 JSON 객체 바로 전송
//@ResponseBody
public class MemberRestController {
    @PostMapping("/restLogin")
    public Map<String, Object> restLogin(@RequestBody Member member) {
        boolean result = false;
        if(member.getId().equals("test") && member.getPass().equals("1234")) result = true;

        //Map 객체(key, value)를 생성하여 전송 --> 자동으로 JSON 객체로 변환
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", result);
        response.put("member", member);

        return response; //호출한 페이지로 문자열 JSON 객체로 전송
    }

//    [방법 1/2]
//    @PostMapping("/restSignup")
//    public Map<String, Object> restSignup(@RequestBody Member member) { //넘어온 JSON객체를 Member에 넣겠다.
//        boolean result = false;
//        if(!member.getId().isEmpty() && !member.getName().isEmpty() && !member.getPass().isEmpty() && !member.getAddress().isEmpty())
//            result = true;
//
//        Map<String, Object> response = new HashMap<String, Object>();
//        response.put("result", result);
//        response.put("member", member);
//
//
//        return response;
//    }

//    [방법 2/2]
    @PostMapping("/restSignup")
    public Member restSignup(@RequestBody Member member) { //넘어온 JSON객체를 Member에 넣겠다.
        return member;
    }

}
