package com.manager.accountmanagementapi.repository;

import com.manager.accountmanagementapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
