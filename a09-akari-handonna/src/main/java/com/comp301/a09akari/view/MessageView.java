package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class MessageView implements FXComponent {

  private final ClassicMvcController controller;

  public MessageView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {

    Label solved;

    if (controller.isSolved()) {
      solved = new Label("Congrats! Puzzle Solved!");
      solved.setTextFill(Color.GREEN);
    } else {
      solved = new Label("Not Solved.");
      solved.setTextFill(Color.RED);
    }
    solved.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 20));

    VBox vBox = new VBox(solved);
    vBox.setAlignment(Pos.TOP_CENTER);
    return vBox;
  }
}
