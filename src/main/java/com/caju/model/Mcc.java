package com.caju.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mcc", schema = "caju")
public class Mcc {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category categoryId;

    private String code;

    public Mcc(){}

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
}
