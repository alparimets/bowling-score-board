package org.example.service;

import static org.mockito.Mockito.*;

import java.util.List;
import org.example.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock PrintService printService;
  @Mock RollService rollService;
  @Mock PinDeckService pinDeckService;

  @InjectMocks GameService gameService;

  @Test
  void singlePlayerTestFromExercise() {
    var player = new Player("Anna");
    var expectedRollCount =
        19; // 10 frames (20 rolls) - 2 strike (2 rolls skipped) + final spare bonus role (1)

    when(rollService.roll()).thenReturn(1, 4, 4, 5, 6, 4, 5, 5, 10, 0, 1, 7, 3, 6, 4, 10, 2, 8, 6);

    gameService.startGame(List.of(player));

    verify(printService, times(1)).printGameStartMessage();
    verify(printService, times(10)).printPlayerTurn(player);
    verify(printService, times(expectedRollCount)).printRollHeader(player);
    verify(printService, times(10)).printFrameResult(eq(player), anyInt());
    verify(printService, times(1)).printFinalResult(List.of(player));
    verify(pinDeckService, times(10)).reset();
    verify(rollService, times(expectedRollCount)).roll();
  }

  @Test
  void perfectPlayerTest() {
    var perfectPlayer = new Player("Perfect");

    // Perfect game: 12 rolls of 10 (strike every frame + 2 bonus in 10th)
    when(rollService.roll()).thenReturn(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);

    gameService.startGame(List.of(perfectPlayer));

    verify(printService, times(1)).printGameStartMessage();
    verify(printService, times(10)).printPlayerTurn(perfectPlayer);
    verify(printService, times(12)).printRollHeader(perfectPlayer);
    verify(printService, times(10)).printFrameResult(eq(perfectPlayer), anyInt());
    verify(printService, times(1)).printFinalResult(List.of(perfectPlayer));
    verify(pinDeckService, times(10)).reset();
    verify(rollService, times(12)).roll();
  }
}
