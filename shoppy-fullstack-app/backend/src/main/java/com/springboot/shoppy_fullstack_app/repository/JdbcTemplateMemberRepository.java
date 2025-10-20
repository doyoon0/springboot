package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //커넥션 생성

    }

    @Override
    public int save(Member member) {
        //jdbcTemplate객체를 이용하여 DB의 member 테이블에 insert
        String sql = "insert into member(id, pwd, name, phone, email, mdate) values (?, ?, ?, ?, ?, now())";
        Object[] param = {member.getId(), member.getPwd(), member.getName(), member.getPhone(), member.getEmail()};
        int rows = jdbcTemplate.update(sql,param);

        return rows;
    }

    @Override
    public Long findById(String id) {
        String sql = "select count(id) from member where id = ?";
        Long count = jdbcTemplate.queryForObject(sql,Long.class, id);

        return count;
    }

    @Override
    public String findByIdnPwd(String id) {
        String sql = "select pwd from member where id = ?";
        // 방법 1.
        Member member = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(Member.class), //RowMapper<T>
                id);

        // 방법 2.
//        String encodePwd = jdbcTemplate.queryForObject(sql, String.class, id);
//        return encodePwd;

        return member.getPwd();
    }

}
