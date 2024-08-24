package com.caju.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "balance", schema = "caju")
public class Balance {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account accountId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category categoryId;

    private BigDecimal balance;

    public Balance(){}

    public Balance(Long id, Account accountId, Category categoryId, BigDecimal balance) {
        this.id = id;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.balance = balance;
    }

    public Account getAccountId() {
        return accountId;
    }


    public Category getCategoryId() {
        return categoryId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
