package ru.job4j.todo.utility;

import ru.job4j.todo.model.Account;
import javax.servlet.http.HttpSession;

public class Utility {
    public static Account logAccount(HttpSession session) {
        Account account = (Account) session.getAttribute("user");
        if (account == null) {
            account = new Account();
            account.setName("Гость");
        }
        return account;
    }
}
