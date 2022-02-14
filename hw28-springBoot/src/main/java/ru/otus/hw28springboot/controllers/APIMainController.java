package ru.otus.hw28springboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw28springboot.domain.entity.Client;
import ru.otus.hw28springboot.services.DBClientService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class APIMainController {
    private final DBClientService dbClientService;

    @GetMapping
    public List<Client> getAllClients() {
        return dbClientService.getAllClients();
    }

    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody Map<String, Object> param) {
        dbClientService.save(param);
        return ResponseEntity.ok().body("");
    }


}
