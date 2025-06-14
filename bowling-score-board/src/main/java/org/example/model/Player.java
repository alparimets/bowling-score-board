package org.example.model;

public class Player {
  private final String name;
  private boolean frameFinished;
  private final ScoreLine scoreLine;

  public Player(String name) {
    this.name = name;
    this.scoreLine = new ScoreLine();
  }

  public void roll(int pins) {
    frameFinished = false;
    var currentFrame = scoreLine.getCurrentFrame();
    currentFrame.addRoll(pins);

    if (currentFrame.isComplete() && !scoreLine.isComplete()) {
      frameFinished = true;
      scoreLine.advanceToNextFrame();
    }
  }

  public boolean isGameFinished() {
    return scoreLine.isComplete();
  }

  public boolean isFrameFinished() {
    return frameFinished;
  }

  public String getName() {
    return name;
  }

  public ScoreLine getScoreLine() {
    return scoreLine;
  }
}
