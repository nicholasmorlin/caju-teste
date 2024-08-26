package com.caju.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account", schema = "caju")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String document;
    private String name;

    public Account(){}

    public Account(String document, String name) {
        this.document = document;
        this.name = name;
    }

    public Account(Long id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }
}
