package org.example.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameTest {

  @Test
  void constructorThrowsIfPlayersNullOrEmpty() {
    var exception1 = assertThrows(IllegalArgumentException.class, () -> new Game(null));
    var exception2 = assertThrows(IllegalArgumentException.class, () -> new Game(List.of()));

    assertEquals("Game must have at least one player.", exception1.getMessage());
    assertEquals("Game must have at least one player.", exception2.getMessage());
  }

  @Test
  void constructorThrowsIfTooManyPlayers() {
    var players =
        List.of(
            mock(Player.class),
            mock(Player.class),
            mock(Player.class),
            mock(Player.class),
            mock(Player.class));
    var exception = assertThrows(IllegalArgumentException.class, () -> new Game(players));

    assertEquals("Cannot have more than 4 players.", exception.getMessage());
  }

  @Test
  void getCurrentPlayerReturnsFirstPlayerInitially() {
    var p1 = mock(Player.class);
    var p2 = mock(Player.class);
    var game = new Game(List.of(p1, p2));

    assertEquals(p1, game.getCurrentPlayer());
  }

  @Test
  void nextPlayerCyclesThroughPlayers() {
    var p1 = mock(Player.class);
    var p2 = mock(Player.class);
    var game = new Game(List.of(p1, p2));

    game.nextPlayer();
    assertEquals(p2, game.getCurrentPlayer());

    game.nextPlayer();
    assertEquals(p1, game.getCurrentPlayer());
  }

  @Test
  void isFinishedReturnsTrueIfAllPlayersFinished() {
    var p1 = mock(Player.class);
    var p2 = mock(Player.class);
    when(p1.isGameFinished()).thenReturn(true);
    when(p2.isGameFinished()).thenReturn(true);

    var game = new Game(List.of(p1, p2));

    assertTrue(game.isFinished());
  }

  @Test
  void isFinishedReturnsFalseIfAnyPlayerNotFinished() {
    var p1 = mock(Player.class);
    var p2 = mock(Player.class);
    when(p1.isGameFinished()).thenReturn(true);
    when(p2.isGameFinished()).thenReturn(false);

    var game = new Game(List.of(p1, p2));

    assertFalse(game.isFinished());
  }
}
