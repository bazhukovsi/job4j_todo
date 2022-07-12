package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@ThreadSafe
public class ItemStore {
    private final SessionFactory sf;

    public ItemStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        int count = session.createQuery("update Item t set t.name = :newName, t.description = :newDesc, "
                        + "t.created = :newCreated, t.done = :newDone  where t.id = :idItem")
                .setParameter("newName", item.getName())
                .setParameter("newDesc", item.getDescription())
                .setParameter("newCreated", new Date())
                .setParameter("newDone", item.isDone())
                .setParameter("idItem", id)
                .executeUpdate();
        session.getTransaction().commit();
        return count > 0;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int count = session.createQuery("delete from Item where id = :idItem")
                .setParameter("idItem", id)
                .executeUpdate();
        session.getTransaction().commit();
        return count > 0;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> items = new ArrayList<>();
        session.beginTransaction();
        Query query = session.createQuery("from Item order by id");
        for (Object item : query.list()) {
            items.add((Item) item);
        }
        session.getTransaction().commit();
        return items;
    }

    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> items = new ArrayList<>();
        session.beginTransaction();
        Query queryByIdWithParamName = session.createQuery("from Item t where t.name = :nameItem");
        queryByIdWithParamName.setParameter("nameItem", key);
        for (Object item : queryByIdWithParamName.list()) {
            items.add((Item) item);
        }
        session.getTransaction().commit();
        return items;
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query queryById = session.createQuery("from Item t where t.id = :idItem");
        queryById.setParameter("idItem", id);
        Item item = (Item) queryById.uniqueResult();
        session.getTransaction().commit();
        return item;
    }

    public List executedItems() {
        return findItemsForDone(true);
    }

    public List notExecutedItems() {
        return findItemsForDone(false);
    }

    private List findItemsForDone(boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Item t where t.done  = :condition");
        List result = query.setParameter("condition", condition).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void executeById(int id) {
        changeIsDone(id, true);
    }

    public void restoreById(int id) {
        changeIsDone(id, false);
    }

    private void changeIsDone(int id, boolean condition) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("update Item t set t.done = :isDone where t.id = :id");
        query.setParameter("isDone", condition);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
