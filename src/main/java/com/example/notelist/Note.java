package com.example.notelist;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Label;

/**
 * <p> </p>
 * Note Object that has content and date.
 */
public class Note implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String message;
  private final String date;

  /**
   * <p> </p>
   * Constructor for Note.
   */
  public Note(String message) {
    this.message = message;
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    this.date = currentDateTime.format(formatter);
  }

  public Label visualizeLabel() {
    return new Label("Message: " + message + "\nCreated at: " + date);
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
