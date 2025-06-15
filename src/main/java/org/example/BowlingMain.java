package org.example;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.example.service.GameService;
import org.example.service.PlayerService;
import org.example.service.PrintService;

@QuarkusMain
public class BowlingMain implements QuarkusApplication {
  private final PrintService printService;
  private final PlayerService playerService;
  private final GameService gameService;

  public BowlingMain(
      PrintService printService, PlayerService playerService, GameService gameService) {
    this.printService = printService;
    this.playerService = playerService;
    this.gameService = gameService;
  }

  @Override
  public int run(String... args) {
    printService.printWelcomeMessage();
    var players = playerService.getPlayers();
    printService.printPlayerNames(players);
    gameService.startGame(players);

    return 0;
  }
}
