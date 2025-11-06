package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcTemplateProductRepository implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ProductDto> findAll() {
        String sql = "select pid, name, price, info, rate, trim(image) as image, imgList from product";
        List<ProductDto> list = jdbcTemplate.query( sql, new BeanPropertyRowMapper<>(ProductDto.class));


        return list;
    }

    @Override
    public ProductDto findById(int pid) {
        String sql = "select pid, name, price, info, rate, trim(image) as image, imgList from product where pid = ?";
        ProductDto product = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductDto.class), pid);
        return product;
    }

    @Override
    public ProductDetailinfoDto findDetailinfo(int pid) {
        String sql = "select did, title_en as titleEn, title_ko as titleKo, pid, list" +
                        " from product_detailinfo where pid = ?";
        ProductDetailinfoDto productDetail = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductDetailinfoDto.class), pid);
        return productDetail;
    }

    @Override
    public List<ProductQnaDto> findQna(int pid) {
         String sql = """
                 select qid, 
                        title, 
                        content, 
                        is_complete as isComplete, 
                        is_lock as isLock, 
                        id, 
                        pid, 
                        cdate
                 from product_qna 
                 where pid = ?
                 """;

        List<ProductQnaDto> productQna = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductQnaDto.class), pid);
        return productQna;
    }

    @Override
    public ProductReturnDto findReturn() {
        String sql = """
                select  rid,
                        title,
                        description,
                        list
                from product_return
                """;
        ProductReturnDto productReturn = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductReturnDto.class));
        return productReturn;
    }
}
