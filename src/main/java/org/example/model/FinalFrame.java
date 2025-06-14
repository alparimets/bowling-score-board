package org.example.model;

public class FinalFrame extends Frame {

  @Override
  public boolean isComplete() {
    return (rolls.size() == 3 || (rolls.size() == 2 && !isStrike() && !isSpare()));
  }
}
