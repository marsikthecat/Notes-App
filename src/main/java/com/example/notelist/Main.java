package com.example.notelist;

import com.example.notelist.ui_Komponents.NoteElement;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main program that displays and adds notes.
 * Note: 40 lines.
 * NoteList: 99 lines.
 * NoteElement: 71 lines.
 * Main: 124 lines.
 * 334 lines of Code.
 */

public class Main extends Application {
  private Notelist notelist = Notelist.loadNote();

  @Override
  public void start(Stage stage) {
    if (notelist == null) {
      notelist = new Notelist();
    }
    ListView<NoteElement> table = new ListView<>();
    TextField field = new TextField();
    Button btn = new Button("Add Note");
    Label notification = new Label();
    notification.setVisible(false);
    notification.setPadding(new Insets(5, 0, 0, 5));
    btn.setOnAction(e -> addNote(field.getText(), notelist, table, notification));

    for (Map.Entry<Integer, Note> entry : notelist.getNoteHashMap()) {
      Note note = entry.getValue();
      NoteElement noteElement = new NoteElement(note);
      noteElement.setId(entry.getKey());
      setUpActionsForNoteElementButtons(table, notification, noteElement, notelist);

    }
    HBox ioDisplay = new HBox();
    ioDisplay.setPadding(new Insets(10, 0, 0, 10));
    ioDisplay.getChildren().addAll(field, btn, notification);
    VBox display = new VBox(5);
    display.getChildren().addAll(ioDisplay, table);
    display.setOnMouseClicked(e -> notification.setVisible(false));
    Scene scene = new Scene(display, 500, 360);
    stage.setOnCloseRequest(e -> notelist.saveNote());
    stage.setTitle("Notes");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }

  private void setUpActionsForNoteElementButtons(ListView<NoteElement> table,
         Label notification, NoteElement noteElement, Notelist notelist) {
    noteElement.setOnEdit(() -> editNote(notelist, noteElement, notification));
    noteElement.setOnDelete(() -> {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirm");
      alert.setContentText("Are you sure to delete the Note");
      ButtonType yesButton = new ButtonType("Yes");
      ButtonType noButton = new ButtonType("No");
      alert.getButtonTypes().setAll(yesButton, noButton);
      ButtonType result = alert.showAndWait().orElse(noButton);
      if (result == yesButton) {
        removeNote(notelist, table, noteElement, notification);
      } else if (result == noButton) {
        System.out.println("nope");
      }

    });
    table.getItems().add(noteElement);
  }

  private void editNote(Notelist notelist, NoteElement noteElement, Label n) {
    int id = noteElement.getTheId();
    TextInputDialog textInputDialog = new TextInputDialog();
    textInputDialog.setResizable(false);
    textInputDialog.setTitle("Edit Note");
    textInputDialog.setHeaderText("");
    textInputDialog.setContentText("Type in the new content of the note:");
    textInputDialog.showAndWait();
    if (textInputDialog.getResult() != null) {
      notelist.editNote(id, textInputDialog.getResult());
      noteElement.setLabelContent(textInputDialog.getResult());
      showNotification(n, "Note edited successfully!");
    }
  }

  private static void showNotification(Label n, String message) {
    n.setVisible(true);
    n.setText(message);
  }

  private void addNote(String noteContent, Notelist notelist,
        ListView<NoteElement> table, Label n) {
    Note note = new Note(noteContent);
    int id = notelist.addNote(note);
    NoteElement noteElement = new NoteElement(note);
    noteElement.setId(id);
    setUpActionsForNoteElementButtons(table, n, noteElement, notelist);
    showNotification(n, "Note added successfully!");
  }

  private void removeNote(Notelist notelist,
        ListView<NoteElement> table, NoteElement noteElement, Label n) {
    notelist.removeNote(noteElement.getTheId());
    table.getItems().remove(noteElement);
    showNotification(n, "Note deleted successfully!");
  }

  public static void main(String[] args) {
    launch();
  }
}