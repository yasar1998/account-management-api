package com.manager.accountmanagementapi.repository;

import com.manager.accountmanagementapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
