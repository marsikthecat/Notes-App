package com.example.notelist.ui;

import com.example.notelist.model.Note;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    noteLabel.setStyle("-fx-font-size: 18px; -fx-padding: 8; -fx-font-weight: bold");
    noteLabel.setWrapText(true);
    String date = note.getDate();

    HBox boxWithLabel = new HBox(noteLabel);
    boxWithLabel.setPadding(new Insets(2));
    Label dateLabel = new Label();
    dateLabel.setText(date);
    dateLabel.setStyle("-fx-font-size: 9px; -fx-padding: 5;");

    editButton = new Button("✏️");
    deleteButton = new Button("🗑️");
    HBox buttonBar = new HBox(10, editButton, deleteButton);
    buttonBar.setAlignment(Pos.BASELINE_LEFT);

    HBox dateLabelBox = new HBox(dateLabel);
    dateLabelBox.setAlignment(Pos.BASELINE_RIGHT);
    HBox boxWithRest = new HBox(dateLabelBox, buttonBar);

    this.getChildren().addAll(boxWithLabel, boxWithRest);
    this.setSpacing(10);
    this.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-color: #dcdcdc;");
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