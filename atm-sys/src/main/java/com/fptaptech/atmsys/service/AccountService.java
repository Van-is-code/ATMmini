package com.fptaptech.atmsys.service;

import com.fptaptech.atmsys.entity.Account;
import com.fptaptech.atmsys.entity.Transaction;
import com.fptaptech.atmsys.entity.TransactionType;
import com.fptaptech.atmsys.repository.AccountRepository;
import com.fptaptech.atmsys.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public boolean deposit(String senderAccountNumber, String recipientAccountNumber, Double amount) {
        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNumber);
        Account recipientAccount = accountRepository.findByAccountNumber(recipientAccountNumber);
        if (senderAccount == null || recipientAccount == null) {
            throw new IllegalArgumentException("Account not found");
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);
        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);

        Transaction transaction = new Transaction();
        transaction.setAccount(senderAccount);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transactionRepository.save(transaction);
        return true;
    }

    @Transactional
    public boolean withdraw(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAW);
        transactionRepository.save(transaction);
        return true;
    }

    public List<Account> getAccountsByUser(String username) {
        return accountRepository.findByUserUsername(username);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}