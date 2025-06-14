package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Scanner;

@ApplicationScoped
public class InputReaderService {
  @Inject PrintService printService;

  private final Scanner scanner = new Scanner(System.in);

  public int getFirstRoll() {
    return getRoll(
        0,
        "Enter the number of pins knocked down or just press ENTER for a random number (0-10): ");
  }

  public int getSecondRoll(int alreadyKnockedDown) {
    return getRoll(
        alreadyKnockedDown,
        "Enter the number of pins knocked down or just press ENTER for a random number (0-"
            + (10 - alreadyKnockedDown)
            + "): ");
  }

  private int getRoll(int alreadyKnockedDown, String prompt) {
    printService.printSimpleMessage(prompt);
    while (true) {
      String input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        int pins = getRandomPins(alreadyKnockedDown);
        printService.printSimpleMessage("Randomly knocked down " + pins + " pins.");
        return pins;
      }
      if (tryParseAndValidate(input, alreadyKnockedDown)) {
        int pins = Integer.parseInt(input);
        printService.printSimpleMessage("Knocked down " + pins + " pins.");
        return pins;
      }
    }
  }

  private boolean tryParseAndValidate(String input, int alreadyKnockedDown) {
    try {
      int numberOfPins = Integer.parseInt(input);
      if (valid(numberOfPins, alreadyKnockedDown)) {
        return true;
      }
      printService.printError(
          "Invalid input. Please enter a number between 0 and " + (10 - alreadyKnockedDown) + ".");
    } catch (NumberFormatException e) {
      printService.printError("Invalid input. Please enter a valid number.");
    }
    return false;
  }

  private int getRandomPins(int alreadyKnockedDown) {
    // This method should return a random number of pins based on the already knocked down pins
    return (int) (Math.random() * (10 - alreadyKnockedDown + 1));
  }

  private boolean valid(int pins, int alreadyKnockedDown) {
    return pins >= 0 && pins <= 10 && (pins + alreadyKnockedDown) <= 10;
  }
}
