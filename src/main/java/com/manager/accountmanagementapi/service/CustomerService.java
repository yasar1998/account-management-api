package com.manager.accountmanagementapi.service;

import com.manager.accountmanagementapi.dto.CustomerDto;
import com.manager.accountmanagementapi.dto.converter.CustomerDtoConverter;
import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.exception.CustomerNotFoundException;
import com.manager.accountmanagementapi.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerDtoConverter converter;


    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    public CustomerDto getCustomerRecordById(String id){
        return converter.convertToCustomerDto(getCustomerById(id));
    }

    public List<CustomerDto> getAllCustomerRecords(){
        return customerRepository.findAll().stream().map(x -> converter.convertToCustomerDto(x))
                .collect(Collectors.toList());
    }


    protected Customer getCustomerById (String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by id: " + id));
    }
}
