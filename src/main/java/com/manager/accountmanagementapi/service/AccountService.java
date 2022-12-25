package com.manager.accountmanagementapi.service;

import com.manager.accountmanagementapi.dto.AccountDto;
import com.manager.accountmanagementapi.dto.converter.AccountDtoConverter;
import com.manager.accountmanagementapi.dto.input.CreateAccountRequest;
import com.manager.accountmanagementapi.entity.Account;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.entity.Transaction;
import com.manager.accountmanagementapi.entity.TransactionType;
import com.manager.accountmanagementapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {

    private final AccountDtoConverter converter;

    private final AccountRepository accountRepository;

    private final CustomerService customerService;

    public AccountService(AccountDtoConverter converter, AccountRepository accountRepository,
                          CustomerService customerService) {
        this.converter = converter;
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }


    public AccountDto createAccount(CreateAccountRequest createAccountRequest){

        Customer customer = customerService.getCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(createAccountRequest.getBalance(), LocalDateTime.now(), customer);

        if(createAccountRequest.getBalance().compareTo(BigDecimal.ZERO) > 0){
            Transaction transaction = new Transaction(TransactionType.INITIAL,
                    createAccountRequest.getBalance(),
                    LocalDateTime.now(),
                    account);

            account.getTransactions().add(transaction);

            accountRepository.save(account);
        }
        return converter.convertToAccountDto(account);
    }
}
