package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_qna")
@Getter
@Setter
public class ProductQna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int qid;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 200)
    private String content;

    @Column(name = "is_complete", columnDefinition = "TINYINT")
    private boolean isComplete;

    @Column(name = "is_lock", columnDefinition = "TINYINT")
    private Boolean isLock;

    @Column(length = 50, nullable = false)
    private String id;

    @Column(nullable = false)
    private int pid;

    private String cdate;
}