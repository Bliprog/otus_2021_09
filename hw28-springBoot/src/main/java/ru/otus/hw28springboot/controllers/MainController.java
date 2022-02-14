package ru.otus.hw28springboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.hw28springboot.services.DBClientService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class MainController {
    private final DBClientService dbClientService;

    @GetMapping
    public String getIndex(Model model) {
        var clients = dbClientService.getAllClients();
        model.addAttribute("clients", clients);
        return "index";
    }
}
