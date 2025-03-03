package com.fptaptech.atmsys.controller;

import com.fptaptech.atmsys.service.AccountService;
import com.fptaptech.atmsys.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        double totalBalance = accounts.stream().mapToDouble(Account::getBalance).sum();
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);
        return "index";
    }

    @GetMapping("/balance")
    public String balance(Model model, Principal principal) {
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        model.addAttribute("accounts", accounts);
        return "balance";
    }

    @GetMapping("/deposit")
    public String deposit(Model model, Principal principal, @RequestParam(required = false) Long accountId) {
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        Account account = (accountId != null) ? accountService.getAccountById(accountId) : (accounts.isEmpty() ? null : accounts.get(0));
        model.addAttribute("account", account);
        model.addAttribute("accounts", accounts);
        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam String senderAccountNumber, @RequestParam String recipientAccountNumber, @RequestParam Double amount, Model model) {
        try {
            accountService.deposit(senderAccountNumber, recipientAccountNumber, amount);
            model.addAttribute("accountNumber", senderAccountNumber);
            model.addAttribute("amount", amount);
            model.addAttribute("type", "Deposit");
            return "success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("accountNumber", senderAccountNumber);
            model.addAttribute("amount", amount);
            model.addAttribute("type", "Deposit");
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/withdraw")
    public String withdraw(Model model, Principal principal, @RequestParam(required = false) Long accountId) {
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        Account account = (accountId != null) ? accountService.getAccountById(accountId) : (accounts.isEmpty() ? null : accounts.get(0));
        model.addAttribute("account", account);
        model.addAttribute("accounts", accounts);
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accountNumber, @RequestParam Double amount, Model model) {
        try {
            accountService.withdraw(accountNumber, amount);
            model.addAttribute("accountNumber", accountNumber);
            model.addAttribute("amount", amount);
            model.addAttribute("type", "Withdraw");
            return "success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("accountNumber", accountNumber);
            model.addAttribute("amount", amount);
            model.addAttribute("type", "Withdraw");
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}