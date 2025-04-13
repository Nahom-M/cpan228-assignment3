package com.example.assignment1.controller;

import com.example.assignment1.data.ItemRepository;
import com.example.assignment1.controller.DistributionCentreDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ItemRepository itemRepository;
    private final RestTemplate restTemplate;

    public AdminController(ItemRepository itemRepository, RestTemplate restTemplate) {
        this.itemRepository = itemRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/manage")
    public String manageItems(Model model) {
        model.addAttribute("items", itemRepository.findAll());

        String url = "http://localhost:8081/distribution-centres";
        DistributionCentreDTO[] centresArray = restTemplate.getForObject(url, DistributionCentreDTO[].class);
        List<DistributionCentreDTO> centres = Arrays.asList(centresArray);
        model.addAttribute("centres", centres);

        return "admin/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return "redirect:/admin/manage";
    }
}
