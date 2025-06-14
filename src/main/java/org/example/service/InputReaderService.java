package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.Scanner;

@ApplicationScoped
public class InputReaderService {
  @Inject PrintService printService;
  private final Scanner scanner = new Scanner(System.in);

  public Optional<String> readLine() {
    var input = scanner.nextLine();
    if (input.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(input);
  }
}
