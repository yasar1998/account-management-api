package com.manager.accountmanagementapi.dto.converter;

import com.manager.accountmanagementapi.dto.CustomerDto;
import com.manager.accountmanagementapi.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    private final ConverterUtils converterUtils;

    public CustomerDtoConverter(ConverterUtils converterUtils) {
        this.converterUtils = converterUtils;
    }

    public CustomerDto convertToCustomerDto(Customer customer){
        return new CustomerDto(customer.getId(), customer.getName(), customer.getSurname(),
                customer.getAccounts().stream().map(x -> converterUtils.convertToCustomerAccountDto(x))
                        .collect(Collectors.toSet()));
    }


}
