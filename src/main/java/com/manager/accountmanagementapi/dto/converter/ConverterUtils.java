package com.manager.accountmanagementapi.dto.converter;

import com.manager.accountmanagementapi.dto.AccountCustomerDto;
import com.manager.accountmanagementapi.dto.AccountTransactionDto;
import com.manager.accountmanagementapi.dto.CustomerAccountDto;
import com.manager.accountmanagementapi.entity.Account;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConverterUtils {

    public CustomerAccountDto convertToCustomerAccountDto(Account account){
        return new CustomerAccountDto(account.getId(), account.getBalance(), account.getCreationDate(),
                account.getTransactions()
                        .stream().map(x -> convertToAccountTransactionDto(x)).collect(Collectors.toSet()));
    }

    protected AccountTransactionDto convertToAccountTransactionDto(Transaction transaction){
        return new AccountTransactionDto(transaction.getId(), transaction.getTransactionType(),
                transaction.getAmount(), transaction.getTransactionDate());
    }

    protected AccountCustomerDto convertToAccountCustomerDto(Customer customer){
        return new AccountCustomerDto(customer.getId(), customer.getName(), customer.getSurname());
    }

}
