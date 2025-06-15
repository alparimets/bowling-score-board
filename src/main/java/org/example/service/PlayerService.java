package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.example.model.Player;

@ApplicationScoped
public class PlayerService {
  private final List<String> playerNames = List.of("Ana", "Bent");

  public List<Player> getPlayers() {
    return playerNames.stream().map(Player::new).toList();
  }
}
