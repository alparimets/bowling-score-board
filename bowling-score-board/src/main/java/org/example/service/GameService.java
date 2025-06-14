package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.example.model.Game;
import org.example.model.Player;

@ApplicationScoped
public class GameService {
  @Inject PrintService printService;
  @Inject InputReaderService inputReaderService;

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

    int currentFrameIndex = currentPlayer.getScoreLine().getCurrentFrameIndex();
    printService.printFrameHeader(currentFrameIndex + 1, 1);

    var firstRollPins = inputReaderService.getFirstRoll();

    currentPlayer.roll(firstRollPins);
    if (!currentPlayer.isFrameFinished()) {
      printService.printFrameHeader(currentFrameIndex + 1, 2);
      var secondRollPins = inputReaderService.getSecondRoll(firstRollPins);
      currentPlayer.roll(secondRollPins);
    }
    var finishedFrame = currentPlayer.getScoreLine().getFrame(currentFrameIndex);
    printService.printFrameResult(currentPlayer, finishedFrame, currentFrameIndex + 1);
  }
}
