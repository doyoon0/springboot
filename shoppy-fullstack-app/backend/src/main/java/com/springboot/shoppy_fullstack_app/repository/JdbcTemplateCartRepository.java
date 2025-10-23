package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.CartItem;
import com.springboot.shoppy_fullstack_app.dto.CartListResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    @Override
    public List<CartListResponse> findList(CartItem cartItem) {
        String sql = """
                    select
                        m.id
                        , p.pid
                        , p.name
                        , p.image
                        , p.price
                        , c.size
                        , c.qty
                        , c.cid
                    from member as m
                    inner join cart as c on m.id = c.id
                    left outer join product as p on p.pid = c.pid
                    where m.id = ?;
                """;
        List<CartListResponse> cartList =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CartListResponse.class), cartItem.getId());
        return cartList;
    }

    @Override
    public int deleteItem(CartItem cartItem) {
        String sql = "delete from cart where cid = ?";
        return jdbcTemplate.update(sql, cartItem.getCid());
    }
}
