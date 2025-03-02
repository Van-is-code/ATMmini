package com.fptaptech.atmsys.service;

import com.fptaptech.atmsys.entity.Transaction;
import com.fptaptech.atmsys.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccount_Id(accountId);
    }
}