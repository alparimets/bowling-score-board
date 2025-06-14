package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.example.model.Player;

@ApplicationScoped
public class PlayerService {
  // This service can be used to manage player-related operations
  // such as creating players, retrieving player statistics, etc.
  List<String> playerNames = List.of("Ana", "Bent");

  public List<Player> getPlayers() {
    return playerNames.stream().map(Player::new).toList();
  }
}
