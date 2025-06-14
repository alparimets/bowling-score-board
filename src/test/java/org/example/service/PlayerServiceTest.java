package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerServiceTest {

  @Test
  void testGetPlayersReturnsDefaultPlayers() {
    var playerService = new PlayerService();

    var players = playerService.getPlayers();

    assertNotNull(players);
    assertEquals(2, players.size());
    assertEquals("Ana", players.get(0).getName());
    assertEquals("Bent", players.get(1).getName());
  }
}
