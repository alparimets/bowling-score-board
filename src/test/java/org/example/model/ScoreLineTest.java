package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreLineTest {

  @Test
  void testInitialScoreLine() {
    var scoreLine = new ScoreLine();

    assertNotNull(scoreLine);
    assertEquals(0, scoreLine.calculateScore());
    assertFalse(scoreLine.isComplete());
  }
}
