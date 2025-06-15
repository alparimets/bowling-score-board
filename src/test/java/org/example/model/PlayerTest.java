package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

  @Test
  void constructorSetsNameAndInitialState() {
    var name = "Player 1";
    var p = new Player(name);

    assertEquals(name, p.getName());
    assertNotNull(p.getScoreLine());
    assertFalse(p.isGameFinished());
  }

  @Test
  void addScoreUpdatesRollLineAndFrameState() {
    var p = new Player("Player 1");
    var initialFrame = p.getScoreLine().getCurrentFrameIndex();
    p.addRoll(5);
    p.addRoll(3);
    var afterRollFrameIndex = p.getScoreLine().getCurrentFrameIndex();

    assertEquals(8, p.getScoreLine().calculateScore());
    assertNotEquals(initialFrame, afterRollFrameIndex);
    assertFalse(p.isGameFinished());
  }

  @Test
  void addRollFinishesGameAfterTenFrames() {
    var p = new Player("Player 1");

    for (int i = 0; i < 12; i++) {
      p.addRoll(10);
    }

    assertTrue(p.isGameFinished());
    assertEquals(300, p.getScoreLine().calculateScore());
  }
}
