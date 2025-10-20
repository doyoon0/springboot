package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Override
    public int signup(Member member){
        //password encoding
        String encodePwd = passwordEncoder.encode(member.getPwd()); //UUID 타입으로 생성됨
        member.setPwd(encodePwd);

        return memberRepository.save(member);
    }

    @Override
    public boolean idCheck(String id) {
        boolean result = true;
        Long count = memberRepository.findById(id);
        if(count == 0) result = false;

        return result;
    }

    @Override
    public boolean login(Member member) {
        String encodePwd = memberRepository.findByIdnPwd(member.getId());
        boolean result = passwordEncoder.matches(member.getPwd(), encodePwd);

        return result;
    }
}
