package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemServiceImpl;
import java.util.Date;

@Controller
@ThreadSafe
public class ItemController {
    ItemServiceImpl itemService;

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String items(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }

    @GetMapping("/executed")
    public String executed(Model model) {
        model.addAttribute("items", itemService.executedItems());
        return "index";
    }

    @GetMapping("/newItems")
    public String newItems(Model model) {
        model.addAttribute("items", itemService.notExecutedItems());
        return "index";
    }

    @GetMapping("/formAddItem")
    public String addItem(Model model) {
        model.addAttribute("", new Item("name", "description",
                new Date(), true));
        return "createItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/index";
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        Item temp = itemService.findById(item.getId());
        itemService.replace(temp.getId(), item);
        return "redirect:/index";
    }

    @GetMapping("/itemProfile/{itemId}")
    public String itemProfile(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "itemProfile";
    }

    @GetMapping("/execute/{itemId}")
    public String executedItem(@PathVariable("itemId") int id) {
        itemService.executeById(id);
        return "redirect:/index";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/index";
    }
}
