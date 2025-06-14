package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.text.MessageFormat;
import org.example.util.NumberParser;

@ApplicationScoped
public class RollService {
  @Inject PrintService printService;
  @Inject PinDeckService pinDeckService;
  @Inject InputReaderService inputReaderService;

  int roll() {
    while (true) {
      printPrompt();
      var input = inputReaderService.readLine();

      if (input.isPresent()) {
        var pins =
            NumberParser.tryParseInt(input.get())
                .filter(this::verifyInput)
                .orElseGet(
                    () -> {
                      printError();
                      return null;
                    });
        if (pins != null) {
          knockDownPins(pins);
          return pins;
        }
      } else {
        int pins = getRandomPins();
        knockDownPins(pins);
        return pins;
      }
    }
  }

  private void printPrompt() {
    printService.printInputPromt(
        MessageFormat.format(
            "Enter the number of pins knocked down or just press ENTER for a random number (0-{0}): ",
            pinDeckService.getRemainingPins()));
  }

  private void printError() {
    printService.printError(
        "Invalid input. Please enter a number between 0 and "
            + pinDeckService.getRemainingPins()
            + ".");
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
