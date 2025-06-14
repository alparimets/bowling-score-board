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
    assertNotNull(scoreLine.getCurrentFrame());
  }

  @Test
  void testAddFrameExceedsLimit() {
    var scoreLine = new ScoreLine();

    // Add 10 frames to reach the limit
    for (int i = 0; i < 9; i++) {
      scoreLine.addFrame(new Frame());
    }

    // Attempt to add an 11th frame should throw an exception
    var exception =
        assertThrows(IllegalStateException.class, () -> scoreLine.addFrame(new Frame()));
    assertEquals("Cannot add more than 10 frames.", exception.getMessage());
  }

  @Test
  void testAdvanceToNextFrame() {
    var scoreLine = new ScoreLine();
    var frame1 = new Frame();
    frame1.addRoll(5);
    frame1.addRoll(3);
    scoreLine.addFrame(frame1);

    assertEquals(8, scoreLine.calculateScore());
    assertFalse(scoreLine.isComplete());

    scoreLine.advanceToNextFrame();
    assertNotNull(scoreLine.getCurrentFrame());
  }
}
