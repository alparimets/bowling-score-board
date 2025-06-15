package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PinDeckServiceTest {

  @InjectMocks PinDeckService sut;

  @Test
  void testInitialRemainingPins() {
    assertEquals(10, sut.getRemainingPins());
  }

  @Test
  void testKnockDownPinsReducesRemainingPins() {
    sut.knockDownPins(3);

    assertEquals(7, sut.getRemainingPins());
  }

  @Test
  void testKnockDownPinsResetsWhenZero() {
    sut.knockDownPins(10);

    verify(sut).reset();
    assertEquals(10, sut.getRemainingPins());
  }

  @Test
  void testKnockDownPinsMultipleTimes() {
    sut.knockDownPins(4);
    sut.knockDownPins(3);
    assertEquals(3, sut.getRemainingPins());
  }

  @Test
  void testResetRestoresPins() {
    sut.knockDownPins(5);
    sut.reset();
    assertEquals(10, sut.getRemainingPins());
  }
}
