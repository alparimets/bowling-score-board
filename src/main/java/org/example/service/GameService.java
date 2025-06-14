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

    int currentTurnFrameIndex = currentPlayer.getScoreLine().getCurrentFrameIndex();
    var activeFrame = currentPlayer.getScoreLine().getFrame(currentTurnFrameIndex);
    printService.printFrameHeader(currentTurnFrameIndex + 1, activeFrame.getRollCount() + 1);
    pinDeckService.reset();

    var firstRollPins = rollService.roll();
    currentPlayer.roll(firstRollPins);
    while (!isTurnFinished(currentPlayer, currentTurnFrameIndex)) {
      printService.printFrameHeader(currentTurnFrameIndex + 1, activeFrame.getRollCount() + 1);
      var secondRollPins = rollService.roll();
      currentPlayer.roll(secondRollPins);
    }

    printService.printFrameResult(currentPlayer, activeFrame, currentTurnFrameIndex + 1);
  }

  private boolean isTurnFinished(Player currentPlayer, int currentTurnFrameIndex) {
    return currentPlayer.getScoreLine().getFrame(currentTurnFrameIndex).isComplete();
  }
}
