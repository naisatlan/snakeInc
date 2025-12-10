package com.snakeinc.api.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerService service;

    @Test
    void testCreatePlayerJunior() {
        PlayerService.PlayerParams params = new PlayerService.PlayerParams("Lili", 12);
        PlayerService.Player player = service.create(params);

        assertNotNull(player);
        assertEquals("Lili", player.name());
        assertEquals(12, player.age());
        assertEquals("JUNIOR", player.category());
        assertEquals(1, player.id());
    }

    @Test
    void testCreatePlayerSenior() {
        PlayerService.PlayerParams params = new PlayerService.PlayerParams("Florian", 21);
        PlayerService.Player player = service.create(params);

        assertNotNull(player);
        assertEquals("Florian", player.name());
        assertEquals(21, player.age());
        assertEquals("SENIOR", player.category());
        assertEquals(1, player.id());
    }

    @Test
    void testGetPlayerById() {
        PlayerService.PlayerParams params = new PlayerService.PlayerParams("Emma", 20);
        PlayerService.Player created = service.create(params);

        PlayerService.Player retrieved = service.getPlayer(created.id());

        assertEquals(created, retrieved);
    }

    @Test
    void testGetPlayerByIdNotFound() {
        PlayerService.Player retrieved = service.getPlayer(999);
        assertNull(retrieved);
    }

    @Test
    void testCreateMultiplePlayersUniqueIds() {
        PlayerService.PlayerParams params1 = new PlayerService.PlayerParams("Alice", 16);
        PlayerService.PlayerParams params2 = new PlayerService.PlayerParams("Bob", 18);

        PlayerService.Player player1 = service.create(params1);
        PlayerService.Player player2 = service.create(params2);

        assertNotEquals(player1.id(), player2.id());
    }
}
