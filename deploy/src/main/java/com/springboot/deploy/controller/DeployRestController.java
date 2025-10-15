package com.springboot.deploy.controller;

import com.springboot.deploy.dto.Deploy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeployRestController {
    @PostMapping("/restLogin")
    public Map<String, Object> restLogin(@RequestBody Deploy deploy) {
        boolean result = false;
        Map<String, Object> response = new HashMap<String, Object>();

        if(deploy.getId().equals("test") && deploy.getPass().equals("1234")) result = true;

        //Map 객체(key, value)를 생성하여 전송  --> 자동으로  JSON 객체로 변환
        response.put("deploy", deploy);
        response.put("result", result);

        return response;
    }

    @PostMapping("/restSignup")
    public Deploy restSignup(@RequestBody Deploy deploy) {
        return deploy;
    }
}
