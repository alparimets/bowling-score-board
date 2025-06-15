package org.example.model;

import java.util.List;

public class Game {
  private final List<Player> players;
  private int currentPlayerIndex = 0;
  private static final int MAXIMUM_PLAYERS = 4;

  public Game(List<Player> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Game must have at least one player.");
    }
    if (players.size() > MAXIMUM_PLAYERS) {
      throw new IllegalArgumentException("Cannot have more than " + MAXIMUM_PLAYERS + " players.");
    }
    this.players = players;
  }

  public void nextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  public boolean isFinished() {
    return players.stream().allMatch(Player::isGameFinished);
  }

  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  public List<Player> getPlayers() {
    return players;
  }
}
