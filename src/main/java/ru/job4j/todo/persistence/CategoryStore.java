package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Repository
@ThreadSafe
public class CategoryStore implements Store {
    private final SessionFactory sf;

    public CategoryStore(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Category> findAll() {
        return tx(session ->
                session.createQuery("from Category").list(), sf);
    }
}
