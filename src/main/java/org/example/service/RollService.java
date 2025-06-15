package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.text.MessageFormat;
import org.example.util.NumberParser;

@ApplicationScoped
class RollService {
  private final PrintService printService;
  private final PinDeckService pinDeckService;
  private final InputReaderService inputReaderService;

  public RollService(
      PrintService printService,
      PinDeckService pinDeckService,
      InputReaderService inputReaderService) {
    this.printService = printService;
    this.pinDeckService = pinDeckService;
    this.inputReaderService = inputReaderService;
  }

  int roll() {
    while (true) {
      printPrompt();
      var input = inputReaderService.readLine();

      if (input.isPresent()) {
        var validatedInput = NumberParser.tryParseInt(input.get()).filter(this::verifyInput);
        if (validatedInput.isPresent()) {
          int pins = validatedInput.get();
          knockDownPins(pins);
          return pins;
        } else {
          printError();
        }
      } else {
        int pins = getRandomPins();
        knockDownPins(pins);
        return pins;
      }
    }
  }

  private void printPrompt() {
    printService.printInputPrompt(
        MessageFormat.format(
            "Enter the number of pins knocked down or just press ENTER for a random number (0-{0}): ",
            pinDeckService.getRemainingPins()));
  }

  private void printError() {
    printService.printError(
        MessageFormat.format(
            "Invalid input. Please enter a number between 0 and {0}.",
            pinDeckService.getRemainingPins()));
  }

  private void knockDownPins(int pins) {
    printService.printSimpleMessage(MessageFormat.format("Knocked down {0} pins.", pins));
    pinDeckService.knockDownPins(pins);
  }

  private boolean verifyInput(int pins) {
    return pins >= 0 && pins <= 10 && pins <= pinDeckService.getRemainingPins();
  }

  private int getRandomPins() {
    return (int) (Math.random() * (pinDeckService.getRemainingPins() + 1));
  }
}
