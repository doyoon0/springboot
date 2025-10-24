package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Support;
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
    public List<Support> findByType(Support support) {
        String stype = support.getStype();
        String sql = "";
        Object[] params;

        if(stype == null || stype.trim().isEmpty()) {
            sql = "select sid, title, content, stype, hits, rdate from support";
            params = new Object[]{};
        } else {
            sql = "select sid, title, content, stype, hits, rdate from support where stype = ?";
            params = new Object[]{stype};
        }
        List<Support> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Support.class), params);
        return list;
    }

}
