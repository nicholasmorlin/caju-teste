package com.caju.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mcc", schema = "caju")
public class Mcc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category categoryId;

    private String code;

    public Mcc(){}

    public Mcc(Category categoryId, String code) {
        this.categoryId = categoryId;
        this.code = code;
    }

    public Mcc(Long id, Category categoryId, String code) {
        this.id = id;
        this.categoryId = categoryId;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public String getCode() {
        return code;
    }
}
