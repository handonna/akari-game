package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class PuzzleView implements FXComponent {

  private final ClassicMvcController controller;

  public PuzzleView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane gp = new GridPane();
    gp.gridLinesVisibleProperty();
    gp.setHgap(1);
    gp.setVgap(1);
    Puzzle puzzle = controller.getActivePuzzle();

    for (int i = 0; i < puzzle.getHeight(); i++) {
      RowConstraints row = new RowConstraints();
      row.setMaxHeight(50);
      row.setMinHeight(50);
      gp.getRowConstraints().add(row);
    }

    for (int j = 0; j < puzzle.getWidth(); j++) {
      ColumnConstraints col = new ColumnConstraints();
      col.setMaxWidth(50);
      col.setMinWidth(50);
      gp.getColumnConstraints().add(col);
      gp.setAlignment(Pos.BOTTOM_CENTER);
    }

    for (int r = 0; r < controller.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < controller.getActivePuzzle().getWidth(); c++) {
        if (puzzle.getCellType(r, c) == CellType.CLUE) {
          StackPane clue = new StackPane();
          Label clueLabel = new Label(Integer.toString(puzzle.getClue(r, c)));
          clueLabel.setTextFill(Color.BLACK);
          clue.getChildren().add(clueLabel);
          gp.add(clue, c, r);
          if (controller.isClueSatisfied(r, c)) {
            clue.setStyle("-fx-background-color: aquamarine");
          } else {
            clue.setStyle("-fx-background-color: lightcoral");
          }
        } else if (puzzle.getCellType(r, c) == CellType.WALL) {
          StackPane wall = new StackPane();
          wall.setStyle("-fx-background-color: slategrey");
          gp.add(wall, c, r);
        } else {
          Button corridor = new Button();
          corridor.setStyle("-fx-background-color: lavender");
          corridor.setMinWidth(50);
          corridor.setMinHeight(50);
          corridor.setMaxWidth(50);
          corridor.setMaxHeight(50);
          gp.add(corridor, c, r);

          int new_r = r;
          int new_c = c;
          corridor.setOnAction(actionEvent -> controller.clickCell(new_r, new_c));

          if (controller.isLit(new_r, new_c)) {
            corridor.setStyle("-fx-background-color: wheat");
          }
          if (controller.isLamp(new_r, new_c)) {
            Image bulb = new Image("light-bulb.png");
            ImageView image = new ImageView(bulb);
            image.setFitHeight(25);
            image.setFitWidth(25);
            corridor.setGraphic(image);
            if (controller.getIsLampIllegal(r, c)) {
              corridor.setStyle("-fx-background-color: red");
            }
          }
        }
      }
    }
    gp.setGridLinesVisible(true);
    // gp.setAlignment(Pos.BOTTOM_CENTER);
    return gp;
  }
}
