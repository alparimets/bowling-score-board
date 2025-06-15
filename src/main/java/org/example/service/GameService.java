package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.example.model.Game;
import org.example.model.Player;
import org.example.model.ScoreLine;

@ApplicationScoped
public class GameService {
  private final PrintService printService;
  private final RollService rollService;
  private final PinDeckService pinDeckService;

  public GameService(
      PrintService printService, RollService rollService, PinDeckService pinDeckService) {
    this.printService = printService;
    this.rollService = rollService;
    this.pinDeckService = pinDeckService;
  }

  public void startGame(List<Player> players) {
    printService.printGameStartMessage();
    Game game = new Game(players);

    do {
      playTurn(game.getCurrentPlayer());
      game.nextPlayer();
    } while (!game.isFinished());
    calculateFinalResults(game);
    printService.printFinalResult(game.getPlayers());
  }

  private void calculateFinalResults(Game game) {
    game.getPlayers().stream().map(Player::getScoreLine).forEach(ScoreLine::calculateScore);
  }

  private void playTurn(Player currentPlayer) {
    printService.printPlayerTurn(currentPlayer);

    int currentFrameNumber = currentPlayer.getCurrentFrameNumber();
    pinDeckService.reset();

    do {
      printService.printRollHeader(currentPlayer);
      int pinsKnockedDown = rollService.roll();
      currentPlayer.addRoll(pinsKnockedDown);
    } while (!isTurnFinished(currentPlayer, currentFrameNumber));

    printService.printFrameResult(currentPlayer, currentFrameNumber);
  }

  private boolean isTurnFinished(Player currentPlayer, int currentFrameNumber) {
    return currentPlayer.isRoundFinished(currentFrameNumber);
  }
}
