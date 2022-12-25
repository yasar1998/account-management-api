package com.manager.accountmanagementapi.dto.converter;

import com.manager.accountmanagementapi.dto.AccountDto;
import com.manager.accountmanagementapi.entity.Account;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountDtoConverter {

    private final ConverterUtils converterUtils;

    public AccountDtoConverter(ConverterUtils converterUtils) {
        this.converterUtils = converterUtils;
    }

    public AccountDto convertToAccountDto(Account account){
        return new AccountDto(account.getId(), account.getBalance(), account.getCreationDate(),
                converterUtils.convertToAccountCustomerDto(account.getCustomer()),
                account.getTransactions()
                        .stream().map(converterUtils::convertToAccountTransactionDto).collect(Collectors.toSet()));
    }
}
