package org.example.model;

public class Player {
  private final String name;
  private final ScoreLine scoreLine;

  public Player(String name) {
    this.name = name;
    this.scoreLine = new ScoreLine();
  }

  public void roll(int pins) {
    var currentFrame = scoreLine.getCurrentFrame();
    currentFrame.addRoll(pins);

    if (currentFrame.isComplete() && !scoreLine.isComplete()) {
      scoreLine.advanceToNextFrame();
    }
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
}
