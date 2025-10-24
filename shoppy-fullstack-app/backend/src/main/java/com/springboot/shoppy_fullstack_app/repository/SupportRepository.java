package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Support;

import java.util.List;

public interface SupportRepository {
    List<Support> findByType(Support support);
}
