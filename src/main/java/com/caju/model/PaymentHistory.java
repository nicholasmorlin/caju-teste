package com.caju.model;

import com.caju.controllers.dto.request.PaymentAuthorizerRequest;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "payment_history", schema = "caju")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mccId", referencedColumnName = "id")
    private Mcc mccId;

    private BigDecimal amount;

    private String merchant;

    public PaymentHistory() {}

    public PaymentHistory(Account accountId, Mcc mccId, BigDecimal amount, String merchant) {
        this.accountId = accountId;
        this.mccId = mccId;
        this.amount = amount;
        this.merchant = merchant;
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
}
