package com.manager.accountmanagementapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.accountmanagementapi.dto.input.CreateAccountRequest;
import com.manager.accountmanagementapi.entity.Account;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.entity.TransactionType;
import com.manager.accountmanagementapi.repository.AccountRepository;
import com.manager.accountmanagementapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAccountRequest_whenCustomerIdIsBlank_shouldGiveErrorResponse() throws Exception {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("", BigDecimal.valueOf(100));

        mockMvc.perform(post("/v1/account").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.customerId", equalTo("Customer id must not be empty")));

    }

    @Test
    public void testCreateAccountRequest_whenInitialCreditIsLessThanZero_shouldGiveErrorResponse() throws Exception {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("123", BigDecimal.valueOf(-5));

        mockMvc.perform(post("/v1/account").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.initialCredit",
                        equalTo("Initial Credit must not be negative value")));

    }

    @Test
    public void testCreateAccountRequest_whenCustomerDoesNotExist_shouldGiveErrorResponse() throws Exception {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest("customer-id",
                BigDecimal.valueOf(200));

        mockMvc.perform(post("/v1/account").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(content().string("Customer not found by id: " +
                        createAccountRequest.getCustomerId()))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testCreateAccountRequest_whenCustomerExists_createAccountWithoutTransaction() throws Exception {

        Customer customer = new Customer("Yashar", "Mustafayev");
        customerRepository.save(customer);

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(customer.getId(), BigDecimal.valueOf(0));

        mockMvc.perform(post("/v1/account").contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerDto.name", equalTo("Yashar")))
                .andExpect(jsonPath("$.customerDto.id", equalTo(customer.getId())))
                .andExpect(jsonPath("$.customerDto.surname", equalTo("Mustafayev")))
                .andExpect(jsonPath("$.accountTransactions", equalTo(List.of())))
                .andExpect(jsonPath("$.balance", equalTo(0)));
    }

    @Test
    public void testCreateAccountRequest_whenCustomerExists_createAccountWithTransaction() throws Exception {

        Customer customer = new Customer("Yashar", "Mustafayev");
        customerRepository.save(customer);

        CreateAccountRequest createAccountRequest = new CreateAccountRequest(customer.getId(), BigDecimal.valueOf(200));

        mockMvc.perform(post("/v1/account").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerDto.name", equalTo("Yashar")))
                .andExpect(jsonPath("$.customerDto.id", equalTo(customer.getId())))
                .andExpect(jsonPath("$.customerDto.surname", equalTo("Mustafayev")))
                .andExpect(jsonPath("$.accountTransactions[0].amount", equalTo(200)))
                .andExpect(jsonPath("$.balance", equalTo(200)))
                .andExpect(jsonPath("$.accountTransactions[0].transactionType", equalTo("INITIAL")));

    }
}