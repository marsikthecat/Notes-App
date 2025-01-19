package com.example.notelist.utils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This helper class for ui for easier customized dialoges.
 */

public class BaseDialog {

  private final Stage stage;
  private final VBox layout;
  private final Button btn1;
  private final Button btn2;

  /**
   * Constructor which determines the fundamentals of the Dialog.
   */

  public BaseDialog(String title, String button1Text, String button2Text) {
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle(title);
    stage.setResizable(false);

    btn1 = new Button(button1Text);
    btn2 = new Button(button2Text);

    layout = new VBox(15);
    layout.getStyleClass().add("root");
  }

  /**
   * Shows a confirmation dialog with the given content text.
   */
  public boolean showConfirmationDialog(String contentText) {
    AtomicBoolean confirmationResult = new AtomicBoolean(false);
    Label contentTextLabel = new Label(contentText);
    contentTextLabel.getStyleClass().add("label");

    btn1.setOnAction(e -> {
      confirmationResult.set(true);
      stage.close();
    });
    btn2.setOnAction(e -> stage.close());

    btn1.getStyleClass().add("button");
    btn2.getStyleClass().add("button");

    HBox buttonBox = createButtonBox();
    layout.getChildren().setAll(contentTextLabel, buttonBox);

    stage.setScene(createStyledScene(layout));
    stage.showAndWait();
    return confirmationResult.get();
  }

  /**
     * Shows a text input dialog with a given message and default text.
     */
  public String showTextInputDialog(String message, String defaultText) {
    AtomicReference<String> inputResult = new AtomicReference<>(null);
    TextField textField = new TextField(defaultText);
    textField.getStyleClass().add("text-field");


    btn1.setOnAction(e -> {
      inputResult.set(textField.getText());
      stage.close();
    });
    btn2.setOnAction(e -> {
      inputResult.set(null);
      stage.close();
    });
    textField.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        inputResult.set(textField.getText());
        stage.close();
      }
    });
    btn1.getStyleClass().add("button");
    btn2.getStyleClass().add("button");

    HBox buttonBox = createButtonBox();
    layout.getChildren().setAll(new Label(message), textField, buttonBox);

    stage.setScene(createStyledScene(layout));
    stage.showAndWait();
    return inputResult.get();
  }

  /**
   * Creates a button box for the input screen.
   */
  private HBox createButtonBox() {
    HBox buttonBox = new HBox(10, btn1, btn2);
    buttonBox.setPadding(new Insets(10));
    buttonBox.setStyle("-fx-alignment: center;");
    return buttonBox;
  }

  private Scene createStyledScene(VBox layout) {
    Scene scene = new Scene(layout);
    scene.getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/BaseDialog.css")).toExternalForm());
    return scene;
  }
}