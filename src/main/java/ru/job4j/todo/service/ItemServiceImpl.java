package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemStore;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    ItemStore itemStore;

    public ItemServiceImpl(ItemStore itemStore) {
        this.itemStore = itemStore;
    }

    @Override
    public Item add(Item item) {
        return itemStore.add(item);
    }

    @Override
    public boolean replace(int id, Item item) {
        return itemStore.replace(id, item);
    }

    @Override
    public boolean delete(int id) {
        return itemStore.delete(id);
    }

    @Override
    public List<Item> findAll() {
        return itemStore.findAll();
    }

    @Override
    public List<Item> findByName(String key) {
        return itemStore.findByName(key);
    }

    @Override
    public Item findById(int id) {
        return itemStore.findById(id);
    }

    @Override
    public List executedItems() {
        return itemStore.executedItems();
    }

    @Override
    public List notExecutedItems() {
        return itemStore.notExecutedItems();
    }

    @Override
    public void executeById(int id) {
        itemStore.executeById(id);
    }

}
