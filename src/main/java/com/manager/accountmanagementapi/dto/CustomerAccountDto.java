package com.manager.accountmanagementapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class CustomerAccountDto {
    private String id;

    private BigDecimal balance;

    private LocalDateTime creationDate;

    private Set<AccountTransactionDto> accountTransactions;
}
