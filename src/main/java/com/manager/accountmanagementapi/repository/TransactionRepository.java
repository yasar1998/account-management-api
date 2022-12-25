package com.manager.accountmanagementapi.repository;

import com.manager.accountmanagementapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
