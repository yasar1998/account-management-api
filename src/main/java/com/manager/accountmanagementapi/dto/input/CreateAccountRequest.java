package com.manager.accountmanagementapi.dto.input;

import java.math.BigDecimal;

public class CreateAccountRequest {

    private String customerId;

    private BigDecimal balance;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
