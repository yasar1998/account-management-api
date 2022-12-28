package com.manager.accountmanagementapi.service;

import com.manager.accountmanagementapi.dto.CustomerDto;
import com.manager.accountmanagementapi.dto.converter.CustomerDtoConverter;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.exception.CustomerNotFoundException;
import com.manager.accountmanagementapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter converter;


    @BeforeEach
    public void setUp(){
        customerRepository = Mockito.mock(CustomerRepository.class);
        converter = Mockito.mock(CustomerDtoConverter.class);
        customerService = new CustomerService(customerRepository, converter);
    }

    @Test
    void testFindCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = new Customer("id", "name", "surname", Set.of());
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById("id");

        Mockito.verify(customerRepository, Mockito.times(1)).findById("id");

        assertEquals(result, customer);
    }

    @Test
    void testFindCustomerById_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundThrowException() {
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById("id"));

    }

    @Test
    void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = new Customer("id", "name", "surname", Set.of());
        CustomerDto customerDto = new CustomerDto("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerRecordById("id");

        Mockito.verify(customerRepository, Mockito.times(1)).findById("id");
        Mockito.verify(converter, Mockito.times(1)).convertToCustomerDto(customer);

        assertEquals(result, customerDto);
    }

    @Test
    void testGetCustomerById_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundThrowException() {
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById("id"));

        Mockito.verifyNoInteractions(converter);
    }

    @Test
    void testGetAllCustomers(){
        List<Customer> customerList =
                List.of(new Customer("id", "name", "surname", Set.of()),
                        new Customer("id1", "name1", "surname1", Set.of()));

        List<CustomerDto> customerDtoList =
                List.of(new CustomerDto("id", "name", "surname", Set.of()),
                        new CustomerDto("id1", "name1", "surname1", Set.of()));

        Mockito.when(customerRepository.findAll()).thenReturn(customerList);

        Mockito.when(converter.convertToCustomerDto(customerList.get(0))).thenReturn(customerDtoList.get(0));
        Mockito.when(converter.convertToCustomerDto(customerList.get(1))).thenReturn(customerDtoList.get(1));

        List<CustomerDto> results = customerService.getAllCustomerRecords();

        assertEquals(customerDtoList.size(), results.size());
        assertEquals(customerDtoList.get(0), results.get(0));
        assertEquals(customerDtoList.get(1), results.get(1));
    }

}