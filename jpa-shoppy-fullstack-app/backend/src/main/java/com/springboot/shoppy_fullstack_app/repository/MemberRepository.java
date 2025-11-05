package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberDto> findByMember(String id); //없어도 에러안남
    int save(MemberDto member);
    Long findById(String id);
    String findByIdnPwd(String id);
}
