package org.example;

import static org.mockito.Mockito.*;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import jakarta.inject.Inject;
import java.util.List;
import org.example.service.GameService;
import org.example.service.PlayerService;
import org.example.service.PrintService;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
public class BowlingMainIT {

  @Inject BowlingMain bowlingMain;

  @InjectMock PrintService printService;

  @InjectMock PlayerService playerService;

  @InjectMock GameService gameService;

  @Test
  void testRun() throws Exception {
    var players = List.of("Alice", "Bob");
    when(playerService.getPlayers()).thenReturn(players);

    int result = bowlingMain.run();

    verify(printService).printWelcomeMessage();
    verify(playerService).getPlayers();
    verify(printService).printPlayerNames(players);
    verify(gameService).startGame(players);
    assert result == 0;
  }
}
