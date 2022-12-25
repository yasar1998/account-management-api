package com.manager.accountmanagementapi.dto;

import com.manager.accountmanagementapi.entity.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTransactionDto {

    private String id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

}
