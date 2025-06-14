package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RollServiceTest {

  @Mock PinDeckService pinDeckService;
  @Mock PrintService printService;

  @InjectMocks RollService sut;

  @Test
  void testValidRolls() {
    when(pinDeckService.getRemainingPins()).thenReturn(10);
    Scanner scanner = new Scanner("\n5\n\n5\n");

    var result = sut.roll();

    assertEquals(4, result);
  }
}
