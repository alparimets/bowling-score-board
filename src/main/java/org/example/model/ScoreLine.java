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
    //        updateBonuses(pins);
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
    return frames.stream().mapToInt(Frame::getTotalPins).sum();
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
}
