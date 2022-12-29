package com.manager.accountmanagementapi.service;

import com.manager.accountmanagementapi.dto.AccountCustomerDto;
import com.manager.accountmanagementapi.dto.AccountDto;
import com.manager.accountmanagementapi.dto.AccountTransactionDto;
import com.manager.accountmanagementapi.dto.converter.AccountDtoConverter;
import com.manager.accountmanagementapi.dto.input.CreateAccountRequest;
import com.manager.accountmanagementapi.entity.Account;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.entity.Transaction;
import com.manager.accountmanagementapi.entity.TransactionType;
import com.manager.accountmanagementapi.exception.CustomerNotFoundException;
import com.manager.accountmanagementapi.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountService accountService;

    private AccountDtoConverter converter;

    private AccountRepository accountRepository;

    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
        converter = Mockito.mock(AccountDtoConverter.class);
        accountRepository = Mockito.mock(AccountRepository.class);
        customerService = Mockito.mock(CustomerService.class);
        accountService = new AccountService(converter, accountRepository, customerService);
    }

    @Test
    void testCreateAccount_whenCustomerIdDoesNotExistAndInitial_shouldThrowCustomerNotFoundException() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("id", BigDecimal.valueOf(100));

        Mockito.when(customerService.getCustomerById("id")).thenThrow(new CustomerNotFoundException("Customer not found by id: id"));

        assertThrows(CustomerNotFoundException.class, () -> accountService.createAccount(createAccountRequest));

        Mockito.verify(customerService, Mockito.times(1)).getCustomerById("id");
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoMoreInteractions(converter);
    }

    @Test
    void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsZero_shouldCreateAccountWithoutTransaction() {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("id", BigDecimal.ZERO);

        Customer customer = new Customer("id", "name", "surname");

        Account account = new Account("id",
                createAccountRequest.getInitialCredit(),
                LocalDateTime.of(2020, Month.DECEMBER, 15, 5, 20),
                customer,
                new HashSet<>()
                );

        AccountCustomerDto accountCustomerDto = new AccountCustomerDto("id", "name", "surname");

        AccountDto accountDto = new AccountDto("id",
                createAccountRequest.getInitialCredit(),
                LocalDateTime.of(2020, Month.DECEMBER, 15, 5, 20),
                accountCustomerDto,
                new HashSet<>()
        );

        Mockito.when(customerService.getCustomerById("id")).thenReturn(customer);

        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Mockito.when(converter.convertToAccountDto(Mockito.any(Account.class))).thenReturn(accountDto);

        AccountDto actualDto = accountService.createAccount(createAccountRequest);

        assertEquals(accountDto, actualDto);
    }

    @Test
    void testCreateAccount_whenCustomerIdExistsAndInitialCreditIsMoreThanZero_shouldCreateAccountWithTransaction() {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("customer-id", BigDecimal.ZERO);

        Customer customer = new Customer("customer-id", "name", "surname");


        Transaction transaction = new Transaction("transaction-id",
                TransactionType.INITIAL,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now()
        );

        Account account = new Account("account-id",
                createAccountRequest.getInitialCredit(),
                LocalDateTime.of(2020, Month.DECEMBER, 15, 5, 20),
                customer,
                Set.of(transaction)
        );

        AccountCustomerDto accountCustomerDto = new AccountCustomerDto("customer-id", "name", "surname");

        AccountTransactionDto transactionDto = new AccountTransactionDto("transaction-id",
                TransactionType.INITIAL,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now()
        );

        AccountDto accountDto = new AccountDto("account-id",
                createAccountRequest.getInitialCredit(),
                LocalDateTime.of(2020, Month.DECEMBER, 15, 5, 20),
                accountCustomerDto,
                Set.of(transactionDto)
        );

        Mockito.when(customerService.getCustomerById("customer-id")).thenReturn(customer);

        Mockito.when(accountRepository.save(account)).thenReturn(account);

        Mockito.when(converter.convertToAccountDto(Mockito.any(Account.class))).thenReturn(accountDto);

        AccountDto actualDto = accountService.createAccount(createAccountRequest);

        assertEquals(accountDto, actualDto);


    }
}