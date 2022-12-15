package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private int p_index;
  private boolean[][] lamp;
  private final List<ModelObserver> observers;
  private Puzzle puzzle_current;

  public ModelImpl(PuzzleLibrary library) {
    if (library.size() == 0 || library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    this.p_index = 0;
    this.puzzle_current = library.getPuzzle(0);
    this.lamp = new boolean[this.getActivePuzzle().getHeight()][this.getActivePuzzle().getWidth()];
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= this.puzzle_current.getHeight()
        || c >= this.puzzle_current.getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.puzzle_current.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    this.lamp[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= this.puzzle_current.getHeight()
        || c >= this.puzzle_current.getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.puzzle_current.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    this.lamp[r][c] = false;
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= this.puzzle_current.getHeight()
        || c >= this.puzzle_current.getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.puzzle_current.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    // right direction
    for (int i = c; i < puzzle_current.getWidth(); i++) {
      if (lamp[r][i]) {
        return true;
      } else if (puzzle_current.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    // left direction
    for (int i = c; i >= 0; i--) {
      if (lamp[r][i]) {
        return true;
      } else if (puzzle_current.getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    // down direction
    for (int i = r; i < puzzle_current.getHeight(); i++) {
      if (lamp[i][c]) {
        return true;
      } else if (puzzle_current.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }

    // up direction
    for (int i = r; i >= 0; i--) {
      if (lamp[i][c]) {
        return true;
      } else if (puzzle_current.getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return this.lamp[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (lamp[r][c] == false) {
      throw new IllegalArgumentException();
    }
    // right
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (lamp[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    // left
    for (int i = c - 1; i >= 0; i--) {
      if (lamp[r][i]) {
        return true;
      } else if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
    }

    // up
    for (int i = r - 1; i >= 0; i--) {
      if (lamp[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    // down
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (lamp[i][c]) {
        return true;
      } else if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.library.getPuzzle(this.p_index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.p_index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException();
    } else {
      p_index = index;
      resetPuzzle();
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }
  // editing

  @Override
  public void resetPuzzle() {
    Puzzle active_puzzle = getActivePuzzle();
    puzzle_current = getActivePuzzle();
    this.lamp = new boolean[active_puzzle.getHeight()][active_puzzle.getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < this.puzzle_current.getHeight(); i++) {
      for (int j = 0; j < this.puzzle_current.getWidth(); j++) {
        if (this.puzzle_current.getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          }
          if (this.lamp[i][j]) {
            if (isLampIllegal(i, j)) {
              return false;
            }
          }
        } else if (this.puzzle_current.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r > puzzle_current.getHeight() - 1 || c > puzzle_current.getWidth() - 1) {
      throw new IndexOutOfBoundsException();
    }
    if (puzzle_current.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int clue_num = puzzle_current.getClue(r, c);
    int tracker = 0;
    if (r > 0 && lamp[r - 1][c]) {
      tracker++;
    }
    if (r < puzzle_current.getHeight() - 1 && lamp[r + 1][c]) {
      tracker++;
    }
    if (c > 0 && lamp[r][c - 1]) {
      tracker++;
    }
    if (c < puzzle_current.getWidth() - 1 && lamp[r][c + 1]) {
      tracker++;
    }
    return (tracker == clue_num);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    this.observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver mo : observers) {
      mo.update(this);
    }
  }
}
