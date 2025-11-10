package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.SupportDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateSupportRepository implements SupportRepository{

    // 우리가 만든 객체가 아니라 spring에 내장된 객체이므로 AutoWired 필요없음.
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateSupportRepository(DataSource datasource) {
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<SupportDto> findAll(SupportDto support) {
        String sql = """
                select sid, title, stype, hits, rdate from support
                    where stype = ?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupportDto.class), support.getStype());
    }


    @Override
    public List<SupportDto> findAll() {
        String sql = """
                select sid, title, stype, hits, rdate from support
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SupportDto.class));
    }
}
