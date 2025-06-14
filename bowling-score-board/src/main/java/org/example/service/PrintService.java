package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import java.util.List;
import org.example.model.Frame;
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

  public void printGameStartMessage() {
    System.out.println("The game is starting! Good luck to all players!");
  }

  public void printPlayerTurn(Player currentPlayer) {
    System.out.println("It's " + currentPlayer.getName() + "'s turn.");
  }

  public void printFrameHeader(int frameNumber, int rollNumber) {
    System.out.println(MessageFormat.format("  Frame {0}, roll {1}:", frameNumber, rollNumber));
  }

  public void printSimpleMessage(String message) {
    System.out.println(message);
  }

  public void printError(String errorMessage) {
    System.err.println("Error: " + errorMessage);
  }

  public void printFrameResult(Player currentPlayer, Frame finishedFrame, int frameIndex) {
    var additionalFrameMessage =
        finishedFrame.isStrike() ? " (strike)" : finishedFrame.isSpare() ? " (spare)" : "";

    System.out.println(
        MessageFormat.format(
            "  {0}''s frame {1} is finished with {2} pins knocked down {3}.",
            currentPlayer.getName(),
            frameIndex,
            finishedFrame.getTotalPins(),
            additionalFrameMessage));
    System.out.println("_________________________________________________________________");
  }
}
