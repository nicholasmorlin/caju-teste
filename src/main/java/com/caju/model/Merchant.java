package com.caju.model;

import jakarta.persistence.*;

@Entity
@Table(name = "merchant", schema = "caju")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    private String name;

    public Merchant(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public Merchant(Long id, Category category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }

    public Merchant() {}

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}
