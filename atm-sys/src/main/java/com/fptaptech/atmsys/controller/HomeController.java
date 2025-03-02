package com.fptaptech.atmsys.controller;

import com.fptaptech.atmsys.entity.Account;
import com.fptaptech.atmsys.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }
        List<Account> accounts = accountService.getAccountsByUser(principal.getName());
        model.addAttribute("accounts", accounts);
        return "index";
    }

}