package org.example.model;

public class FinalFrame extends Frame {

  @Override
  public boolean isComplete() {
    return (rolls.size() == 3 || (rolls.size() == 2 && !isStrike() && !isSpare()));
  }

  @Override
  public int getBonus() {
    // The Last frame only gets bonus from itself, if any. So anything above 10 is a bonus.
    return Math.max(0, getFrameScore() - 10);
  }
}
