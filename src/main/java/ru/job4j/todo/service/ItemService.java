package ru.job4j.todo.service;

import ru.job4j.todo.model.Item;
import java.util.List;

public interface ItemService {
    boolean replace(int id, Item todo);

    boolean delete(int id);

    List<Item> findAll();

    List<Item> findByName(String key);

    Item findById(int id);

    List<Item> executedItems();

    List<Item> notExecutedItems();

    void executeById(int id);

    Item add(Item item, List<String> categories);

}
