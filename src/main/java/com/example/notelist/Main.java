package com.example.notelist;

import com.example.notelist.model.Note;
import com.example.notelist.model.Notelist;
import com.example.notelist.ui.NoteElement;
import com.example.notelist.utils.BaseDialog;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main program for Note management.
 * Note: 37 lines.
 * NoteList: 103 lines.
 * NoteElement: 86 lines.
 * BaseDialog: 115 lines.
 * Main: 149 lines.
 * 490 lines + 63 lines CSS.
 */

public class Main extends Application {
  private final Notelist notelist = new Notelist();
  private ListView<NoteElement> table;
  private Label notification;

  @Override
  public void start(Stage stage) {
    notelist.loadNote();
    table = new ListView<>();
    table.setFocusTraversable(false);
    table.setCellFactory(list -> new ListCell<>() {
      @Override
      protected void updateItem(NoteElement item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setGraphic(null);
          setText(null);
        } else {
          setGraphic(item);
          getStyleClass().add("list-cell");
        }
      }
    });

    TextField field = new TextField();
    field.setPromptText("Type in your new note:");
    field.setFocusTraversable(false);

    Button btn = new Button("Add Note");
    btn.setOnAction(e -> addNote(field.getText()));

    notification = new Label();
    notification.setVisible(false);
    notification.setPadding(new Insets(5, 0, 0, 5));

    for (Map.Entry<UUID, Note> entry : notelist.getNoteHashMap().entrySet()) {
      Note note = entry.getValue();
      NoteElement noteElement = new NoteElement(note);
      noteElement.setId(entry.getKey());
      setUpActionsForNoteElementButtons(noteElement);
    }

    HBox ioDisplay = new HBox();
    ioDisplay.setPadding(new Insets(10, 0, 0, 10));
    ioDisplay.getChildren().addAll(field, btn, notification);

    VBox display = new VBox(5);
    display.getChildren().addAll(ioDisplay, table);
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

  private void setUpActionsForNoteElementButtons(NoteElement noteElement) {
    noteElement.setOnEdit(() -> editNote(noteElement));
    noteElement.setOnDelete(() -> {
      BaseDialog baseDialog = new BaseDialog("Confirm", "Yes", "No");
      boolean result = baseDialog.showConfirmationDialog("Are you sure to delete the Note?");
      if (result) {
        removeNote(noteElement);
      }
    });
    table.getItems().add(noteElement);
  }

  private void editNote(NoteElement noteElement) {
    UUID id = noteElement.getTheId();
    BaseDialog baseDialog = new BaseDialog("Edit Note", "Edit", "Cancel");
    String result = baseDialog.showTextInputDialog("Type in the new content of the note:",
            noteElement.getNoteLabelContent());

    if (result != null) {
      notelist.editNote(id, result);
      noteElement.setLabelContent(result);
      showNotification("Note edited successfully!");
    }
  }

  private void showNotification(String message) {
    notification.setVisible(true);
    notification.setText(message);
  }

  private void addNote(String noteContent) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String noteDate = currentDateTime.format(formatter);
    Note note = new Note(noteContent, noteDate);
    notelist.addNote(note);
    NoteElement noteElement = new NoteElement(note);
    noteElement.setId(note.getId());
    setUpActionsForNoteElementButtons(noteElement);
    showNotification("Note added successfully!");
  }

  private void removeNote(NoteElement noteElement) {
    notelist.removeNote(noteElement.getTheId());
    table.getItems().remove(noteElement);
    showNotification("Note deleted successfully!");
  }

  /**
   * .
   */
  public static void main(String[] args) {
    launch();
  }
}