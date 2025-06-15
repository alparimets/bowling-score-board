package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Frame {
  final List<Integer> rolls = new ArrayList<>();
  private boolean isStrike = false;
  private boolean isSpare = false;
  private int bonus = 0;

  public boolean isStrike() {
    return isStrike;
  }

  public boolean isSpare() {
    return isSpare;
  }

  public void addRoll(int pins) {

    rolls.add(pins);
    if (rolls.size() == 1 && pins == 10) {
      isStrike = true;
    } else if (rolls.size() == 2 && (rolls.get(0) + rolls.get(1) == 10)) {
      isSpare = true;
    }
  }

  public boolean isComplete() {
    return rolls.size() == 2 || isStrike;
  }

  public int getFrameScore() {
    return rolls.stream().mapToInt(Integer::intValue).sum() + bonus;
  }

  public List<Integer> getRolls() {
    return new ArrayList<>(rolls);
  }

  public int getRollCount() {
    return rolls.size();
  }

  public void addBonus(int bonus) {
    this.bonus += bonus;
  }

  public int getBonus() {
    return bonus;
  }
}
