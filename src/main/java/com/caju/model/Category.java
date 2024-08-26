package com.caju.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category", schema = "caju")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    public Category(){}

    public Category(String type) {
        this.type = type;
    }

    public Category(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
