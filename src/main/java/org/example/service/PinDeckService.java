package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PinDeckService {
  @Inject PrintService printService;

  private static final int TOTAL_PINS = 10;
  private int remainingPins = TOTAL_PINS;

  public void reset() {
    this.remainingPins = TOTAL_PINS;
  }

  public void knockDownPins(int pins) {
    remainingPins -= pins;
    if (remainingPins == 0) {
      reset();
    }
  }

  public int getRemainingPins() {
    return remainingPins;
  }
}
