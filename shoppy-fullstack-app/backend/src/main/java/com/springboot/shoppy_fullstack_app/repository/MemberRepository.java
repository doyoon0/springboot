package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByMember(String id); //없어도 에러안남
    int save(Member member);
    Long findById(String id);
    String findByIdnPwd(String id);
}
