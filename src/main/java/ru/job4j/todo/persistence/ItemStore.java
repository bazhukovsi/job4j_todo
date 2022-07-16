package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.util.Date;
import java.util.List;

@Repository
@ThreadSafe
public class ItemStore implements Store {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item, List<String> categoriesId) {
        tx(session -> {
            for (String id : categoriesId) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                item.addCategory(category);
            }
            return session.save(item);
        }, sf);
        return item;
    }

    public boolean replace(int id, Item item) {
        return tx(session -> session.createQuery("update Item t set t.name = :newName, t.description = :newDesc, "
                        + "t.created = :newCreated, t.done = :newDone  where t.id = :idItem")
                .setParameter("newName", item.getName())
                .setParameter("newDesc", item.getDescription())
                .setParameter("newCreated", new Date())
                .setParameter("newDone", item.isDone())
                .setParameter("idItem", id)
                .executeUpdate(), sf) > 0;
    }

    public boolean delete(int id) {
        return tx(session -> session.createQuery("delete from Item where id = :idItem")
                .setParameter("idItem", id)
                .executeUpdate(), sf) > 0;
    }

    public List findAll() {
        return tx(session -> session.createQuery(
                "from Item").list(), sf);
    }

    public List<Item> findByName(String key) {
        return tx(session -> session.createQuery("from Item t where t.name = :nameItem")
                .setParameter("nameItem", key).list(), sf);
    }

    public Item findById(int id) {
        return (Item) tx(session -> session.createQuery("from Item t where t.id = :idItem")
                .setParameter("idItem", id).uniqueResult(), sf);
    }

    public List executedItems() {
        return findItemsForDone(true);
    }

    public List notExecutedItems() {
        return findItemsForDone(false);
    }

    private List findItemsForDone(boolean condition) {
        return tx(session -> session.createQuery("from Item t where t.done  = :condition")
                .setParameter("condition", condition).list(), sf);
    }

    public void executeById(int id) {
        changeIsDone(id, true);
    }

    private void changeIsDone(int id, boolean condition) {
        tx(session -> session.createQuery("update Item t set t.done = :isDone where t.id = :id")
                .setParameter("isDone", condition)
                .setParameter("id", id)
                .executeUpdate(), sf);
    }

}
