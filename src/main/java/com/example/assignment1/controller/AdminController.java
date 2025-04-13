package com.example.assignment1.controller;

import com.example.assignment1.data.ItemRepository;
import com.example.assignment1.model.Item;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ItemRepository itemRepository;

    public AdminController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/manage")
    public String manageItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "admin/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/manage";
    }
}
