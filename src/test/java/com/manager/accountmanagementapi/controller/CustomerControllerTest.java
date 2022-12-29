package com.manager.accountmanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.accountmanagementapi.dto.CustomerDto;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testGetAllCustomers_shouldReturnAllCustomers() throws Exception {
        Customer customer = new Customer("Yashar", "Mustafayev");
        Customer customer1 = new Customer("Mike", "Dean");
        customerRepository.save(customer);
        customerRepository.save(customer1);

        CustomerDto customerDto = new CustomerDto(customer.getId(), "Yashar", "Mustafayev", Set.of());
        CustomerDto customerDto1 = new CustomerDto(customer1.getId(), "Mike", "Dean", Set.of());

        List<CustomerDto>customerDtoList = List.of(customerDto, customerDto1);

        String output = objectMapper.writeValueAsString(customerDtoList);

        mockMvc.perform(get("/v1/customer/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(output));

        System.out.println(output);

    }

    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomerDto() throws Exception {
        Customer customer = new Customer("Yashar", "Mustafayev");

        customerRepository.save(customer);

        String id = customer.getId();

        mockMvc.perform(get("/v1/customer/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(id)))
                .andExpect(jsonPath("$.name", equalTo("Yashar")))
                .andExpect(jsonPath("$.surname", equalTo("Mustafayev")))
                .andReturn();
    }

    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldReturnHttpNotFound() throws Exception {
        mockMvc.perform(get("/v1/customer/113")).andExpect(status().isNotFound())
                .andReturn();
    }
}