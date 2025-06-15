package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FrameTest {
  @ParameterizedTest
  @MethodSource("frameRollsProvider")
  void testNonFinalFrame(int roll1, int roll2, boolean expectedStrike, boolean expectedSpare) {
    var frame = new Frame();
    frame.addRoll(roll1);
    if (!frame.isComplete()) {
      frame.addRoll(roll2);
    }

    assertTrue(frame.isComplete());
    assertEquals(expectedStrike, frame.isStrike());
    assertEquals(expectedSpare, frame.isSpare());
  }

  static Stream<Arguments> frameRollsProvider() {
    return Stream.of(
        Arguments.of(10, 0, true, false), // Strike
        Arguments.of(5, 5, false, true), // Spare
        Arguments.of(3, 4, false, false), // Open frame
        Arguments.of(0, 10, false, true) // Spare with second roll
        );
  }

  @Test
  void testIsCompleteAfterTwoRolls() {
    var frame = new Frame();
    frame.addRoll(3);
    frame.addRoll(4);

    assertTrue(frame.isComplete());
  }

  @Test
  void testIsCompleteAfterStrike() {
    var frame = new Frame();
    frame.addRoll(10);

    assertTrue(frame.isComplete());
    assertTrue(frame.isStrike());
  }

  @Test
  void testAddBonusAndGetFrameScore() {
    var frame = new Frame();
    frame.addRoll(7);
    frame.addRoll(2);
    assertEquals(9, frame.getFrameScore());

    frame.addBonus(5);
    assertEquals(14, frame.getFrameScore());

    frame.addBonus(3);
    assertEquals(17, frame.getFrameScore());
  }
}
