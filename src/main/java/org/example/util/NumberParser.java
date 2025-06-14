package org.example.util;

import java.util.Optional;

public class NumberParser {
  private NumberParser() {}

  public static Optional<Integer> tryParseInt(String value) {
    try {
      return Optional.of(Integer.parseInt(value));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }
}
