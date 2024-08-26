package com.caju.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history", schema = "caju")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account accountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mccId", referencedColumnName = "id")
    private Mcc mccId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category categoryId;

    private BigDecimal amount;

    private String merchant;

    private LocalDateTime creationDate;

    public PaymentHistory() {
    }

    public PaymentHistory(Account accountId, Mcc mccId, BigDecimal amount, String merchant, Category categoryId) {
        this.accountId = accountId;
        this.mccId = mccId;
        this.amount = amount;
        this.merchant = merchant;
        this.categoryId = categoryId;
        this.creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Mcc getMccId() {
        return mccId;
    }

    public void setMccId(Mcc mccId) {
        this.mccId = mccId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
