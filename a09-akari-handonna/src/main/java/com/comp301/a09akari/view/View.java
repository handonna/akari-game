package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final ClassicMvcController controller;

  public View(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox view_layout = new VBox();
    view_layout.getStyleClass().add("control-layout");

    ControlView controlView = new ControlView(controller);
    view_layout.getChildren().add(controlView.render());

    PuzzleView puzzleView = new PuzzleView(controller);
    view_layout.getChildren().add(puzzleView.render());

    MessageView messageView = new MessageView(controller);
    view_layout.getChildren().add(messageView.render());

    return view_layout;
  }
}
