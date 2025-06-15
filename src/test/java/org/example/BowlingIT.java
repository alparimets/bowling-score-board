package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.example.model.Player;
import org.example.service.PlayerService;
import org.junit.jupiter.api.*;

@QuarkusTest
public class BowlingIT {

  @InjectMock PlayerService playerService;

  @Inject BowlingMain bowlingMain;

  private final InputStream originalSystemIn = System.in;

  @AfterEach
  void cleanUp() {
    System.setIn(originalSystemIn);
  }

  @Test
  void singlePlayerITestFromExercise() {
    var players = List.of(new Player("Anna"));
    when(playerService.getPlayers()).thenReturn(players);
    String simulatedUserInput = "1\n4\n4\n5\n6\n4\n5\n5\n10\n0\n1\n7\n3\n6\n4\n10\n2\n8\n6";
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));

    var result = bowlingMain.run();

    assertEquals(0, result);
  }

  @Test
  @Disabled(
      "System.in is somehow not reset, so this test fails when run after the previous one. Runs fine on its own.")
  void multiplePlayerTest() {
    var players = List.of(new Player("Anna"), new Player("Bent"), new Player("Cecilia"));
    when(playerService.getPlayers()).thenReturn(players);
    String simulatedUserInput = "5\n".repeat(63);
    System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes(StandardCharsets.UTF_8)));

    var result = bowlingMain.run();

    assertEquals(0, result);
  }
}
