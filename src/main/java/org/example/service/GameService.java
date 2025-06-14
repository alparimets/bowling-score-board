package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.example.model.Game;
import org.example.model.Player;

@ApplicationScoped
public class GameService {
  @Inject PrintService printService;
  @Inject RollService rollService;
  @Inject PinDeckService pinDeckService;

  private Game game;

  public void startGame(List<Player> players) {
    printService.printGameStartMessage();
    game = new Game(players);

    while (!game.isFinished()) {
      var currentPlayer = game.getCurrentPlayer();

      playTurn(currentPlayer);

      game.nextPlayer();
    }
  }

  private void playTurn(Player currentPlayer) {
    printService.printPlayerTurn(currentPlayer);

    int currentFrameNumber = currentPlayer.getCurrentFrameNumber();
    printService.printRollHeader(currentPlayer);
    pinDeckService.reset();

    var firstRollPins = rollService.roll();
    currentPlayer.roll(firstRollPins);
    while (!isTurnFinished(currentPlayer, currentFrameNumber)) {
      printService.printRollHeader(currentPlayer);
      var secondRollPins = rollService.roll();
      currentPlayer.roll(secondRollPins);
    }

    printService.printFrameResult(currentPlayer, currentFrameNumber);
  }

  private boolean isTurnFinished(Player currentPlayer, int currentFrameNumber) {
    return currentPlayer.isRoundFinished(currentFrameNumber);
  }
}
