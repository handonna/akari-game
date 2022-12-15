package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    int i = this.model.getActivePuzzleIndex() + 1;
    if (i < this.model.getPuzzleLibrarySize()) {
      this.model.setActivePuzzleIndex(i);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    int i = this.model.getActivePuzzleIndex() - 1;
    if (i >= 0) {
      this.model.setActivePuzzleIndex(i);
    }
  }

  @Override
  public void clickRandPuzzle() {

    int random = model.getActivePuzzleIndex();
    while (random == model.getActivePuzzleIndex()) {
      random = (int) (Math.random() * model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(random);
  }

  @Override
  public void clickResetPuzzle() {
    this.model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (this.model.isLamp(r, c) == true) {
      this.model.removeLamp(r, c);
    } else {
      this.model.addLamp(r, c);
    }
  }

  @Override
  public boolean isSolved() {
    return this.model.isSolved();
  }

  @Override
  public void setModel(Model model) {
    this.model = model;
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.model.getActivePuzzleIndex();
  }

  @Override
  public int getLibrarySize() {
    return this.model.getPuzzleLibrarySize();
  }

  @Override
  public boolean isLamp(int r, int c) {
    return this.model.isLamp(r, c);
  }

  @Override
  public boolean getIsLampIllegal(int r, int c) {
    return this.model.isLampIllegal(r, c);
  }

  @Override
  public boolean isLit(int r, int c) {
    return this.model.isLit(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return this.model.isClueSatisfied(r, c);
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.model.getActivePuzzle();
  }
}
