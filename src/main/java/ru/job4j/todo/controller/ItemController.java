package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.AccountService;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemServiceImpl;
import ru.job4j.todo.utility.Utility;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@ThreadSafe
public class ItemController {
    ItemServiceImpl itemService;
    AccountService accountService;
    CategoryService categoryService;

    public ItemController(ItemServiceImpl itemService, AccountService accountService, CategoryService categoryService) {
        this.itemService = itemService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @GetMapping("/index")
    public String items(Model model, HttpSession session) {
        model.addAttribute("items", itemService.findAll());
        Account account = Utility.logAccount(session);
        model.addAttribute("account", account);
        return "index";
    }

    @GetMapping("/executed")
    public String executed(Model model, HttpSession session) {
        model.addAttribute("items", itemService.executedItems());
        Account account = Utility.logAccount(session);
        model.addAttribute("account", account);
        return "index";
    }

    @GetMapping("/newItems")
    public String newItems(Model model, HttpSession session) {
        model.addAttribute("items", itemService.notExecutedItems());
        Account account = Utility.logAccount(session);
        model.addAttribute("account", account);
        return "index";
    }

    @GetMapping("/formAddItem")
    public String addItem(Model model, HttpSession session) {
        model.addAttribute("", new Item("name", "description",
                new Date(), true, new Account()));
        Account account = Utility.logAccount(session);
        model.addAttribute("account", account);
        model.addAttribute("categories", categoryService.findAll());
        return "createItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, @RequestParam(name = "categoriesId") List<String> categoriesId, HttpSession session) {
        Account account = Utility.logAccount(session);
        item.setAccount(account);
        itemService.add(item, categoriesId);
        return "redirect:/index";
    }

    @GetMapping("/updateItem/{itemId}")
    public String updateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, HttpSession session) {
        Account account = Utility.logAccount(session);
        if (account.getName().equals(itemService.findById(item.getId()).getAccount().getName())) {
            itemService.replace(item.getId(), item);
            return "redirect:/index";
        }
        return "failupdate";
    }

    @GetMapping("/itemProfile/{itemId}")
    public String itemProfile(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("item", itemService.findById(id));
        Account account = Utility.logAccount(session);
        model.addAttribute("account", account);
        return "itemProfile";
    }

    @GetMapping("/execute/{itemId}")
    public String executedItem(@PathVariable("itemId") int id, HttpSession session) {
        Account account = Utility.logAccount(session);
        if (account.getName().equals(itemService.findById(id).getAccount().getName())) {
            itemService.executeById(id);
            return "redirect:/index";
        }
        return "failexecute";
    }

    @GetMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable("itemId") int id, HttpSession session) {
        Account account = Utility.logAccount(session);
        if (account.getName().equals(itemService.findById(id).getAccount().getName())) {
            itemService.delete(id);
            return "redirect:/index";
        }
        return "faildelete";
    }
}
