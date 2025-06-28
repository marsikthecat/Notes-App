package com.example.notelist.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Note Object that has content and date.
 */
public class Note implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private final UUID id = UUID.randomUUID();
  private String message;
  private final String date;

  /**
   * Constructor for Note.
   */
  public Note(String message) {
    this.message = message;
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    this.date = currentDateTime.format(formatter);
  }

  public String getDate() {
    return date;
  }

  public String getMessage() {
    return message;
  }

  public UUID getId() {
    return id;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}