package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ScoreLine {
  private final List<Frame> frames = new ArrayList<>();
  private int currentFrameIndex = 0;

  public ScoreLine() {
    // Initialize with the first frame
    addFrame(new Frame());
  }

  public void addFrame(Frame frame) {
    if (frames.size() < 10) {
      frames.add(frame);
    } else {
      throw new IllegalStateException("Cannot add more than 10 frames.");
    }
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
    if (frames.size() < 10) {
      addFrame(new Frame());
      currentFrameIndex++;
    } else {
      throw new IllegalStateException("Already at the last frame.");
    }
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
