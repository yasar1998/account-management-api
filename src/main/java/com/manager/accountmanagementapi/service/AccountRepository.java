package com.manager.accountmanagementapi.service;

import com.manager.accountmanagementapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
