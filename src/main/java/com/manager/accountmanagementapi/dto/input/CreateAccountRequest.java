package com.manager.accountmanagementapi.dto.input;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class CreateAccountRequest {

    @NotBlank(message = "Customer id must not be empty")
    private String customerId;
    @Min(value = 0, message = "Initial Credit must not be negative value")
    private BigDecimal initialCredit;

    public CreateAccountRequest(String customerId, BigDecimal initialCredit) {
        this.customerId = customerId;
        this.initialCredit = initialCredit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
    }
}
