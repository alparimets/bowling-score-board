package org.example.model;

public class Player {
  private final String name;
  private final ScoreLine scoreLine;

  public Player(String name) {
    this.name = name;
    this.scoreLine = new ScoreLine();
  }

  public void roll(int pins) {
    scoreLine.addRoll(pins);
  }

  public boolean isGameFinished() {
    return scoreLine.isComplete();
  }

  public String getName() {
    return name;
  }

  public ScoreLine getScoreLine() {
    return scoreLine;
  }

  public int getCurrentFrameNumber() {
    return scoreLine.getCurrentFrameIndex() + 1;
  }

  public int getCurrentRollNumber() {
    return scoreLine.getCurrentRollNumber();
  }

  public boolean isRoundFinished(int currentFrameNumber) {
    return scoreLine.isRoundFinished(currentFrameNumber);
  }
}
