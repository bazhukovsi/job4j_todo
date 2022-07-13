package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Account;

import java.util.Optional;

@Repository
@ThreadSafe
public class AccountStore {
    private final SessionFactory sf;

    public AccountStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Account> add(Account account) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(account);
    }

    public Optional<Account> findUserByLoginAndPwd(String login, String password) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query queryByLoginAndPwd = session.createQuery("from Account acc where acc.login = :login and acc.password = :password");
        queryByLoginAndPwd.setParameter("login", login);
        queryByLoginAndPwd.setParameter("password", password);
        Account account = (Account) queryByLoginAndPwd.uniqueResult();
        session.getTransaction().commit();
        return Optional.ofNullable(account);
    }
}
