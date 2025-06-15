package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreLine {
  private final List<Frame> frames = new ArrayList<>();
  private int currentFrameIndex = 0;

  public ScoreLine() {
    frames.add(new Frame());
  }

  public boolean isComplete() {
    return frames.size() == 10 && frames.get(9).isComplete();
  }

  public void addRoll(int pins) {
    var currentFrame = frames.get(currentFrameIndex);
    currentFrame.addRoll(pins);
    if (!isComplete() && currentFrame.isComplete()) {
      advanceToNextFrame();
    }
  }

  public void advanceToNextFrame() {
    if (frames.size() < 9) {
      frames.add(new Frame());
    } else if (frames.size() == 9) {
      frames.add(new FinalFrame());
    } else {
      throw new IllegalStateException("Already at the last frame.");
    }
    currentFrameIndex++;
  }

  public int calculateScore() {
    calculateBonuses();
    return frames.stream().mapToInt(Frame::getFrameScore).sum();
  }

  public int getCurrentFrameIndex() {
    return currentFrameIndex;
  }

  public int getCurrentRollNumber() {
    return frames.get(currentFrameIndex).getRollCount() + 1;
  }

  public boolean isRoundFinished(int frameNumber) {
    return getFrame(frameNumber).isComplete();
  }

  public Frame getFrame(int frameNumber) {
    var frameIndex = frameNumber - 1;
    return frames.get(frameIndex);
  }

  private void calculateBonuses() {
    for (int i = 0; i < frames.size() - 1; i++) {
      var frame = frames.get(i);
      if (frame.isSpare() && frames.get(i + 1).getRollCount() > 0) {
        frame.addBonus(frames.get(i + 1).getRolls().getFirst());
      }
      if (frame.isStrike()) {
        var firstBonus = frames.get(i + 1).getRolls().getFirst();
        var secondBonus =
            frames.get(i + 1).getRolls().size() > 1
                ? frames.get(i + 1).getRolls().get(1)
                : frames.get(i + 2).getRolls().getFirst();
        frame.addBonus(firstBonus + secondBonus);
      }
    }
  }
}
