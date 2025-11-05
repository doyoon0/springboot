package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;

public interface MemberService {
    int signup(MemberDto member);
    boolean idCheck(String id);
    boolean login(MemberDto member);
}
