package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class ControlView implements FXComponent {
  private final ClassicMvcController controller;

  public ControlView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox controls = new HBox();
    controls.getStyleClass().add("control-layout");
    Button reset = new Button("Reset");
    reset.getStyleClass().add("reset-button");
    reset.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));
    Button previous = new Button("Previous");
    previous.getStyleClass().add("previous-button");
    previous.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));

    Label index_puzzle =
        new Label(
            " Puzzle "
                + (controller.getActivePuzzleIndex() + 1)
                + " of "
                + (controller.getLibrarySize()));
    index_puzzle.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));

    Button next = new Button("Next");
    next.getStyleClass().add("next-button");
    next.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));

    Button random = new Button("Random");
    random.getStyleClass().add("random-button");
    random.setFont(Font.font("Comic Sans MS", FontPosture.ITALIC, 20));

    index_puzzle.setTextFill(Color.BLACK);

    // actions
    reset.setOnAction(ActionEvent -> controller.clickResetPuzzle());
    previous.setOnAction(ActionEvent -> controller.clickPrevPuzzle());
    next.setOnAction(ActionEvent -> controller.clickNextPuzzle());
    random.setOnAction(ActionEvent -> controller.clickRandPuzzle());

    controls.getChildren().addAll(reset, previous, index_puzzle, next, random);
    return controls;
  }
}
