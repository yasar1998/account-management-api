package com.manager.accountmanagementapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class AccountDto {

    private String id;

    private BigDecimal balance;

    private LocalDateTime creationDate;

    private AccountCustomerDto customer;

    private Set<AccountTransactionDto> accountTransactions;

    public AccountDto(String id, BigDecimal balance, LocalDateTime creationDate,
                      AccountCustomerDto customer, Set<AccountTransactionDto> accountTransactions) {
        this.id = id;
        this.balance = balance;
        this.creationDate = creationDate;
        this.customer = customer;
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

    public AccountCustomerDto getCustomerDto() {
        return customer;
    }

    public void setCustomerDto(AccountCustomerDto customer) {
        this.customer = customer;
    }

    public Set<AccountTransactionDto> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(Set<AccountTransactionDto> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }
}
