package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import java.util.List;
import org.example.model.Player;

@ApplicationScoped
public class PrintService {

  public void printWelcomeMessage() {
    System.out.println("Welcome to the Bowling Game!");
  }

  public void printPlayerNames(List<Player> players) {
    if (players.isEmpty()) {
      System.out.println("No players have been added.");
    } else {
      System.out.println("Current players:");
      for (Player player : players) {
        System.out.println("- " + player.getName());
      }
    }
  }

  void printGameStartMessage() {
    System.out.println("The game is starting! Good luck to all players!");
  }

  void printPlayerTurn(Player currentPlayer) {
    printDivider();
    System.out.println("It's " + currentPlayer.getName() + "'s turn.");
  }

  void printRollHeader(Player player) {
    System.out.println(
        MessageFormat.format(
            "  Frame {0}, roll {1}:",
            player.getCurrentFrameNumber(), player.getCurrentRollNumber()));
  }

  void printSimpleMessage(String message) {
    System.out.println(message);
  }

  void printInputPrompt(String message) {
    System.out.print(message);
  }

  void printError(String errorMessage) {
    System.err.println("Error: " + errorMessage);
  }

  void printFrameResult(Player currentPlayer, int frameNumber) {
    var finishedFrame = currentPlayer.getScoreLine().getFrame(frameNumber);
    var additionalFrameMessage =
        finishedFrame.isStrike() ? " (strike)" : finishedFrame.isSpare() ? " (spare)" : "";

    System.out.println(
        MessageFormat.format(
            "{0}''s frame {1} is finished with {2} pins knocked down {3}.",
            currentPlayer.getName(),
            frameNumber,
            finishedFrame.getFrameScore(),
            additionalFrameMessage));
  }

  void printFinalResult(List<Player> players) {
    System.out.println("The game is over! Here are the final scores:");
    players.forEach(this::printPlayerFinalResult);
  }

  private void printPlayerFinalResult(Player player) {
    int accumulatedScore = 0;
    for (int i = 1; i <= 10; i++) {
      printFrameResult(player, i);
      accumulatedScore += player.getScoreLine().getFrame(i).getFrameScore();
      System.out.println(
          MessageFormat.format("  Bonus: {0}", player.getScoreLine().getFrame(i).getBonus()));
      System.out.println("  Accumulated score after frame " + i + ": " + accumulatedScore);
      printDivider();
    }
  }

  private void printDivider() {
    System.out.println("--------------------------------------------------");
  }
}
