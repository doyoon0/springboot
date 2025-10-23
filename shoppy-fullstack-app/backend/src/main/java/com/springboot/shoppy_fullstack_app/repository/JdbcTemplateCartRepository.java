package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateCartRepository implements CartRepository{
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplateCartRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int add(CartItem cartItem) {
        String sql = """
                    insert into cart(size, qty, pid, id, cdate) 
                    values(?, ?, ?, ?, now())
                """;
        Object[] param = {cartItem.getSize(), cartItem.getQty(), cartItem.getPid(), cartItem.getId()};

        return jdbcTemplate.update(sql, param); //insert, delete, update 는 모두 update로 통일
    }

    @Override
    public CartItem checkQty(CartItem cartItem) {
        String sql = """
                    select
                    	ifnull(max(cid), 0) as cid,
                        count(*) as checkQty
                    from cart
                    where pid = ? and size = ? and id = ?;
                """;
        Object[] param = {cartItem.getPid(), cartItem.getSize(), cartItem.getId()};
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItem.class), param);
    }

    @Override
    public int updateQty(CartItem cartItem) {
        String sql = "";
        if(cartItem.getType().equals("+")) {
            sql = "update cart set qty = qty + 1 where cid = ?";
        } else {
            sql = "update cart set qty = qty - 1 where cid = ?";
        };
        return jdbcTemplate.update(sql, cartItem.getCid());
    }

    @Override
    public CartItem getCount(CartItem cartItem) {
        String sql = "select ifnull(sum(qty),0) as sumQty from cart where id = ?"; // null 처리하여 undefined 발생 방지
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItem.class), cartItem.getId());
    }
}
