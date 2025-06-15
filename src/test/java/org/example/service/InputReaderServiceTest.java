package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class InputReaderServiceTest {
  private final InputStream originalIn = System.in;

  @AfterEach
  void restoreSystemIn() {
    System.setIn(originalIn);
  }

  @Test
  void testReadLineReturnsEmptyOptionalOnEmptyInput() {
    System.setIn(new ByteArrayInputStream("\n".getBytes()));
    InputReaderService sut = new InputReaderService();
    var result = sut.readLine();
    assertTrue(result.isEmpty());
  }

  @Test
  void testReadLineReturnsOptionalWithInput() {
    System.setIn(new ByteArrayInputStream("hello\n".getBytes()));
    InputReaderService sut = new InputReaderService();
    var result = sut.readLine();
    assertTrue(result.isPresent());
    assertEquals("hello", result.get());
  }
}
