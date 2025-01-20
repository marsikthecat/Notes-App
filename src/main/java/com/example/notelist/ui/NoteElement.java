package com.example.notelist.ui;

import com.example.notelist.model.Note;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Graphical representation of a Note using a VBox and some style stuff.
 */

public class NoteElement extends VBox {

  private int id;
  private final Button editButton;
  private final Button deleteButton;
  private final Label noteLabel;

  /**
     * Constructor.
     */

  public NoteElement(Note note) {
    this.noteLabel = new Label(note.getMessage());
    noteLabel.getStyleClass().add("note_label");
    String date = note.getDate();

    Label dateLabel = new Label();
    dateLabel.setText(date);
    dateLabel.getStyleClass().add("date_label");

    editButton = new Button("Edit");
    deleteButton = new Button("Delete");
    editButton.getStyleClass().add("button");
    deleteButton.getStyleClass().add("button");
    HBox buttonBar = new HBox(10, editButton, deleteButton);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox boxWithLabel = new HBox(noteLabel);
    HBox dateLabelBox = new HBox(dateLabel);
    HBox boxWithRest = new HBox(dateLabelBox, spacer, buttonBar);
    this.getChildren().addAll(boxWithLabel, boxWithRest);
    this.getStylesheets().add(Objects.requireNonNull(getClass()
            .getResource("/NoteElement.css")).toExternalForm());
    this.getStyleClass().add("whole_box");
  }

  public void setOnEdit(Runnable action) {
    editButton.setOnAction(e -> action.run());
  }

  public void setOnDelete(Runnable action) {
    deleteButton.setOnAction(e -> action.run());
  }

  public int getTheId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLabelContent(String labelContent) {
    this.noteLabel.setText(labelContent);
  }

  public String getNoteLabelContent() {
    return noteLabel.getText();
  }
}