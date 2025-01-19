package com.example.notelist;

import com.example.notelist.model.Note;
import com.example.notelist.model.Notelist;
import com.example.notelist.ui.NoteElement;
import com.example.notelist.utils.BaseDialog;
import java.util.Map;
import java.util.Objects;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main program for Note management.
 * Note: 40 lines.
 * NoteList: 97 lines.
 * NoteElement: 75 lines.
 * BaseDialog: 120 lines.
 * Main: 126 lines.
 * 458 lines of Code + 50 lines CSS.
 */

public class Main extends Application {
  private Notelist notelist = Notelist.loadNote();

  @Override
  public void start(Stage stage) {
    if (notelist == null) {
      notelist = new Notelist();
    }
    ListView<NoteElement> table = new ListView<>();
    table.setFocusTraversable(false);
    table.getStyleClass().add("list-view");
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
    StackPane root = new StackPane();
    root.getChildren().add(table);

    HBox ioDisplay = new HBox();
    ioDisplay.setPadding(new Insets(10, 0, 0, 10));
    ioDisplay.getChildren().addAll(field, btn, notification);
    VBox display = new VBox(5);
    display.getChildren().addAll(ioDisplay, root);
    display.setOnMouseClicked(e -> notification.setVisible(false));
    Scene scene = new Scene(display, 500, 360);
    scene.getStylesheets().add(
            Objects.requireNonNull(getClass().getResource("/MainWindow.css")).toExternalForm());
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
      BaseDialog baseDialog = new BaseDialog("Confirm", "Yes", "No");
      boolean result = baseDialog.showConfirmationDialog("Are you sure to delete the Note?");
      if (result) {
        removeNote(notelist, table, noteElement, notification);
      }
    });
    table.getItems().add(noteElement);
  }

  private void editNote(Notelist notelist, NoteElement noteElement, Label n) {
    int id = noteElement.getTheId();
    BaseDialog baseDialog = new BaseDialog("Edit Note", "Edit", "Cancel");
    String result = baseDialog.showTextInputDialog("Type in the new content of the note:",
            noteElement.getNoteLabelContent());

    if (result != null) {
      notelist.editNote(id, result);
      noteElement.setLabelContent(result);
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