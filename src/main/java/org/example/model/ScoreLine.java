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

  public Frame getCurrentFrame() {
    if (!frames.isEmpty() && currentFrameIndex < frames.size()) {
      return frames.get(currentFrameIndex);
    } else {
      throw new IllegalStateException("No current frame available.");
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

  public Frame getFrame(int index) {
    if (index < 0 || index >= frames.size()) {
      throw new IndexOutOfBoundsException("Frame index out of bounds.");
    }
    return frames.get(index);
  }
}
