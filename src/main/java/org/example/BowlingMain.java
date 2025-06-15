package org.example;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.example.service.GameService;
import org.example.service.PlayerService;
import org.example.service.PrintService;

@QuarkusMain
public class BowlingMain implements QuarkusApplication {
  @Inject PrintService printService;
  @Inject PlayerService playerService;
  @Inject GameService gameService;

  @Override
  public int run(String... args) {
    printService.printWelcomeMessage();
    var players = playerService.getPlayers();
    printService.printPlayerNames(players);
    gameService.startGame(players);

    return 0;
  }
}
