package com.fptaptech.atmsys.controller;

import com.fptaptech.atmsys.service.TransactionService;
import com.fptaptech.atmsys.entity.Account;
import com.fptaptech.atmsys.entity.Transaction;
import com.fptaptech.atmsys.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String transactions(Model model, Principal principal, @RequestParam(required = false) Long accountId) {
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        List<Transaction> transactions = null;
        Account account = null;
        if (accountId != null) {
            account = accountService.getAccountById(accountId);
            transactions = transactionService.getTransactionsByAccountId(accountId);
        }
        model.addAttribute("account", account);
        model.addAttribute("accounts", accounts);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}