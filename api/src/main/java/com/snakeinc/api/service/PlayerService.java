package com.snakeinc.api.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Service
public class PlayerService {

    private final AtomicInteger idGen = new AtomicInteger(0);
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();

    public Player create(PlayerParams params) {

        int id = idGen.incrementAndGet();
        String category = params.age() < 18 ? "JUNIOR" : "SENIOR";

        Player p = new Player(
                id,
                params.name(),
                params.age(),
                category,
                LocalDate.now()
        );

        players.put(id, p);
        return p;
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public record PlayerParams(
        @NotBlank(message = "Name must be provided") String name,
        @Min(value = 14, message = "Age must be superior to 13") int age
    ) {}
    
    public record Player(int id, String name, int age, String category, LocalDate createdAt) {}
}
