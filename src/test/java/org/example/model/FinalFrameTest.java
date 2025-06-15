package org.example.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FinalFrameTest {

  static Stream<Arguments> isCompleteCases() {
    return Stream.of(
        Arguments.of(List.of(5, 10, 10), true),
        Arguments.of(List.of(3, 5), true),
        Arguments.of(List.of(10, 5), false),
        Arguments.of(List.of(4, 6), false));
  }

  @ParameterizedTest
  @MethodSource("isCompleteCases")
  void isCompleteParameterized(List<Integer> rolls, boolean expected) {
    var frame = new FinalFrame();
    rolls.forEach(frame::addRoll);
    assertEquals(expected, frame.isComplete());
  }

  static Stream<Arguments> bonusCases() {
    return Stream.of(
        Arguments.of(List.of(3, 6), 0),
        Arguments.of(List.of(5, 4, 0), 0),
        Arguments.of(List.of(10, 10, 5), 15));
  }

  @ParameterizedTest
  @MethodSource("bonusCases")
  void getBonusParameterized(List<Integer> rolls, int expectedBonus) {
    var frame = new FinalFrame();
    rolls.forEach(frame::addRoll);
    assertEquals(expectedBonus, frame.getBonus());
  }

  static Stream<Arguments> spareAndStrikeCases() {
    return Stream.of(
        Arguments.of(List.of(0, 3), true, 0),
        Arguments.of(List.of(7, 3), false, 0),
        Arguments.of(List.of(7, 3, 5), true, 5),
        Arguments.of(List.of(10), false, 0),
        Arguments.of(List.of(10, 5), false, 5),
        Arguments.of(List.of(10, 5, 4), true, 9));
  }

  @ParameterizedTest
  @MethodSource("spareAndStrikeCases")
  void spareAndStrikeFinalFrame(List<Integer> rolls, boolean expectedComplete, int expectedBonus) {
    var frame = new FinalFrame();
    rolls.forEach(frame::addRoll);
    assertEquals(expectedComplete, frame.isComplete());
    assertEquals(expectedBonus, frame.getBonus());
  }
}
