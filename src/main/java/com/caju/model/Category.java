package com.caju.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category", schema = "caju")
public class Category {

    @Id
    private Long id;
    private String type;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
