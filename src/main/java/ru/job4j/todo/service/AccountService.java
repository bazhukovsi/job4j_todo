package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.persistence.AccountStore;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountStore userStore;

    public AccountService(AccountStore userStore) {
        this.userStore = userStore;
    }

    public Optional<Account> add(Account account) {
        return userStore.add(account);
    }

    public Optional<Account> findUserByLoginAndPwd(String login, String password) {
        return userStore.findUserByLoginAndPwd(login, password);
    }

}
