package com.manager.accountmanagementapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class CustomerAccountDto {
    private String id;

    private BigDecimal balance;

    private LocalDateTime creationDate;

    private Set<AccountTransactionDto> accountTransactions;

    public CustomerAccountDto(String id, BigDecimal balance, LocalDateTime creationDate,
                              Set<AccountTransactionDto> accountTransactions) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
        this.accountTransactions = accountTransactions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<AccountTransactionDto> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(Set<AccountTransactionDto> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }
}
