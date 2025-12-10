package com.snakeinc.api.controller;

import com.snakeinc.api.service.PlayerService;
import com.snakeinc.api.service.PlayerService.Player;
import com.snakeinc.api.service.PlayerService.PlayerParams;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @PostMapping
    public Player create(@Valid @RequestBody PlayerParams params) {
        return service.create(params);
    }

    @GetMapping("/{id}")
    public Player getById(@PathVariable int id) {
        return service.getPlayer(id);
    }
}
