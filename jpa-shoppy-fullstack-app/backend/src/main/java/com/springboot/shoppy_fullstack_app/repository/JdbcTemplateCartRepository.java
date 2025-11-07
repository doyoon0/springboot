package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import com.springboot.shoppy_fullstack_app.dto.CartListResponseDto;
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
    public List<CartListResponseDto> findList(CartItemDto cartItem) {
        String sql = """
                select id, mname, phone, email, pid, name, info, image, price, size, qty, cid, total_price
                from view_cartlist
                where id = ?
                """;
        Object[] params = { cartItem.getId() };
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(CartListResponseDto.class), params);
    }

    @Override
    public int deleteItem(CartItemDto cartItem) {
        String sql = """
                delete from cart where cid = ?
                """;
        return jdbcTemplate.update(sql, cartItem.getCid());
    }

    @Override
    public CartItemDto getCount(CartItemDto cartItem) {
        String sql = "select ifnull(sum(qty), 0) as sumQty from cart where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItemDto.class), cartItem.getId());
    }

    @Override
    public int updateQty(CartItemDto cartItem) {
        String sql = "";
        if(cartItem.getType().equals("+")) {
            sql = " update cart set qty = qty + 1 where cid =? ";
        } else {
            sql = " update cart set qty = qty - 1 where cid =? ";
        }
//        System.out.println("updateQty :: " + sql);
        return jdbcTemplate.update(sql, cartItem.getCid());
    }

    @Override
    public CartItemDto checkQty(CartItemDto cartItem) {
//        System.out.println("CartRepository :: " + cartItem.getPid() + cartItem.getSize() + cartItem.getId());
        //checkQty 처럼 테이블 컬럼이 아니라 함수로 만들어지는애는 entity가 아니라, dto이다. entity는 테이블과 1:1 매칭이 되어야함.
        String sql = """
                SELECT
                   ifnull(MAX(cid), 0) AS cid,
                   COUNT(*) AS checkQty
                 FROM cart
                 WHERE pid = ? AND size = ? AND id = ?
                """;

        Object[] params = {
                cartItem.getPid(), cartItem.getSize(), cartItem.getId()
        };
        CartItemDto resultCartItem = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CartItemDto.class), params);

//        System.out.println("checkQty :: resultCartItem = " + resultCartItem);
        return resultCartItem;
    }

    @Override
    public int add(CartItemDto cartItem) {
        String sql = """
                insert into cart(size, qty, pid, id, cdate)
                    values(?, ?, ?, ?, now())
                """;
        Object [] params = {
                cartItem.getSize(),
                cartItem.getQty(),
                cartItem.getPid(),
                cartItem.getId()
        };
        return jdbcTemplate.update(sql, params);
    }
}
