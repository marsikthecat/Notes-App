package com.example.notelist;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Map;

/**
 * <p></p>
 * Main program that displays and adds notes.
 */

public class Main extends Application {
  private Notelist notelist = Notelist.loadNote();

  @Override
  public void start(Stage stage) {
    if (notelist == null) {
      notelist = new Notelist();
    }
    ListView<Label> table = new ListView<>();
    TextField field = new TextField();
    Button btn = new Button("Add Note");
    Label notification = new Label();
    notification.setVisible(false);
    notification.setPadding(new Insets(5, 0, 0, 5));
    btn.setOnAction(e -> addNote(field.getText(), notelist, table, notification));
    for (Map.Entry<Integer, Note> entry : notelist.getNoteHashMap()) {
      Label label = entry.getValue().visualizeLabel();
      label.setOnContextMenuRequested(e -> editNote(e, notelist, table, notification));
      label.setId(String.valueOf(entry.getKey()));
      label.setPadding(new Insets(5, 0, 0, 5));
      table.getItems().addAll(label);
    }
    table.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(Label label, boolean empty) {
        super.updateItem(label, empty);
        if (label != null) {
          Button removeButton = new Button("Remove");
          removeButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("Are you sure to delete the Note");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);
            ButtonType result = alert.showAndWait().orElse(noButton);
            if (result == yesButton) {
              removeNote(notelist, table, label, notification);
            } else if (result == noButton) {
              System.out.println("nope");
            }
          });
          VBox container = new VBox(label, removeButton);
          VBox.setMargin(removeButton, new Insets(5, 0, 5, 5));
          setGraphic(container);
        } else {
          setText(null);
          setGraphic(null);
        }
      }
    });

    HBox ioDisplay = new HBox();
    ioDisplay.setPadding(new Insets(10, 0, 0, 10));
    ioDisplay.getChildren().addAll(field, btn, notification);
    VBox display = new VBox(5);
    table.setOnMouseClicked(e -> notification.setVisible(false));
    field.setOnMouseClicked(e -> notification.setVisible(false));
    display.getChildren().addAll(ioDisplay, table);
    Scene scene = new Scene(display, 500, 360);
    stage.setOnCloseRequest( e -> notelist.saveNote());
    stage.setTitle("Notes");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }


  private void editNote(ContextMenuEvent event, Notelist notelist, ListView<Label> table, Label n) {
    Label clickedLabel = (Label) event.getSource();
    int id = Integer.parseInt(clickedLabel.getId());
    TextInputDialog textInputDialog = new TextInputDialog();
    textInputDialog.setResizable(false);
    textInputDialog.setTitle("Edit Note");
    textInputDialog.setHeaderText("");
    textInputDialog.setContentText("Type in the new content of the note:");
    textInputDialog.showAndWait();
    if (textInputDialog.getResult() != null) {
      notelist.editNote(id, textInputDialog.getResult());
      Label newLabel = notelist.getNote(id).visualizeLabel();
      newLabel.setOnContextMenuRequested(e -> editNote(e, notelist, table, n));
      table.getItems().get(table.getItems().indexOf(clickedLabel)).setText(newLabel.getText());
      n.setVisible(true);
      n.setText("Note edited successfully!");
    }
  }

  private void addNote(String noteContent, Notelist notelist, ListView<Label> table, Label n) {
    Note note = new Note(noteContent);
    int id = notelist.addNote(note);
    Label label = note.visualizeLabel();
    label.setId(String.valueOf(id));
    label.setPadding(new Insets(5, 0, 0, 5));
    label.setOnContextMenuRequested(e -> editNote(e, notelist, table, n));
    table.getItems().add(label);
    n.setVisible(true);
    n.setText("Note added successfully!");
  }

  private void removeNote(Notelist notelist, ListView<Label> table, Label label, Label n) {
    notelist.removeNote(Integer.parseInt(label.getId()));
    table.getItems().remove(label);
    n.setVisible(true);
    n.setText("Note deleted successfully!");
  }

  public static void main(String[] args) {
    launch();
  }
}