package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.service.AccountService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/formAddAccount")
    public String addAccount(Model model) {
        model.addAttribute("user", new Account("name", "login", "password"));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute Account account) {
        Optional<Account> regAccount = accountService.add(account);
        if (regAccount.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/formLoginAccount")
    public String loginUser(Model model) {
        model.addAttribute("login", "login");
        model.addAttribute("password", "password");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute Account account, HttpServletRequest req) {
        Optional<Account> accountDb = accountService.findUserByLoginAndPwd(
                account.getLogin(), account.getPassword()
        );
        if (accountDb.isEmpty()) {
            model.addAttribute("fail", true);
            model.addAttribute("login", "login");
            model.addAttribute("password", "password");
            return "login";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", accountDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/success")
    public String successUser(Model model) {
        return "success";
    }

    @GetMapping("/fail")
    public String failUser(Model model) {
        return "fail";
    }
}
