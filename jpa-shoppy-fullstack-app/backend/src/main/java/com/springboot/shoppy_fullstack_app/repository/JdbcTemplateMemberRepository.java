package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //커넥션 생성

    }

    /**
     * Spring-Security의 AuthenticationProvider 객체에 의해 UserDetailsService 호출
     */
    @Override
    public Optional<MemberDto> findByMember(String id) {
        String sql = "select ifnull(MAX(id), null) as id, " +
                " ifnull(MAX(pwd), null) as pwd from member where id = ?";
        try {
            MemberDto member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MemberDto.class), id);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            // 조회 결과가 없을 때 null 반환 대신 Optional.empty()
            return Optional.empty();
        }
    }

    @Override
    public int save(MemberDto member) {
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
        String sql = "select ifnull(MAX(pwd), null) as pwd from member where id = ?";
        // 방법 1.
        MemberDto member = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(MemberDto.class), //RowMapper<T>
                id);

        // 방법 2.
//        String encodePwd = jdbcTemplate.queryForObject(sql, String.class, id);
//        return encodePwd;

        return member.getPwd();
    }

}
