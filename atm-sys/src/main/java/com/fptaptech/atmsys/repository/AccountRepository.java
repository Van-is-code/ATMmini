package com.fptaptech.atmsys.repository;

import com.fptaptech.atmsys.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
   List<Account> findByUserUsername(String username);
   Account findByAccountNumber(String accountNumber);
   List<Account> findByUserId(Long userId);

}