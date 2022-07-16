package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryStore;

import java.util.List;

@Service
public class CategoryService {
    CategoryStore categoryStore;

    public CategoryService(CategoryStore categoryStore) {
        this.categoryStore = categoryStore;
    }

    public List<Category> findAll() {
        return categoryStore.findAll();
    }
}
