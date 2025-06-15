package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RollServiceTest {

  @Mock PrintService printService;
  @Mock PinDeckService pinDeckService;
  @Mock InputReaderService inputReaderService;

  @InjectMocks RollService sut;

  @Test
  void rollReturnsPinsWhenValidUserInput() {
    when(pinDeckService.getRemainingPins()).thenReturn(10);
    when(inputReaderService.readLine()).thenReturn(Optional.of("7"));

    int result = sut.roll();

    assertEquals(7, result);
    var messageCaptor = ArgumentCaptor.forClass(String.class);
    verify(printService).printSimpleMessage(messageCaptor.capture());
    assertEquals("Knocked down 7 pins.", messageCaptor.getValue());
    verify(pinDeckService).knockDownPins(7);
  }

  @Test
  void rollKeepsAskingForNewInputUntilValid() {
    when(pinDeckService.getRemainingPins()).thenReturn(10);
    when(inputReaderService.readLine())
        .thenReturn(Optional.of("abc")) // invalid
        .thenReturn(Optional.of("5")); // valid

    int result = sut.roll();

    assertEquals(5, result);
    var errorMessageCaptor = ArgumentCaptor.forClass(String.class);
    verify(printService).printError(errorMessageCaptor.capture());
    assertEquals(
        "Invalid input. Please enter a number between 0 and 10.", errorMessageCaptor.getValue());
    verify(printService, times(2)).printInputPrompt(anyString());
    verify(pinDeckService).knockDownPins(5);
  }

  @Test
  void rollReturnsRandomValueWhenEmptyInput() {
    var remainingPins = 6;
    when(pinDeckService.getRemainingPins()).thenReturn(remainingPins);
    when(inputReaderService.readLine()).thenReturn(Optional.empty());

    int result = sut.roll();

    assertTrue(result >= 0 && result <= remainingPins);
    var promptCaptor = ArgumentCaptor.forClass(String.class);
    verify(printService).printInputPrompt(promptCaptor.capture());
    assertEquals(
        "Enter the number of pins knocked down or just press ENTER for a random number (0-6): ",
        promptCaptor.getValue());
    verify(pinDeckService).knockDownPins(result);
    verify(printService).printSimpleMessage(anyString());
  }
}
