package com.caju.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account", schema = "caju")
public class Account {

    @Id
    private Long id;
    private String document;
    private String name;

    public Account(){}

    public Account(Long id, String document, String name) {
        this.id = id;
        this.document = document;
        this.name = name;
    }
}
