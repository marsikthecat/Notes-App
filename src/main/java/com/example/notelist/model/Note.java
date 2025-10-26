package com.example.notelist.model;

import java.util.UUID;

/**
 * Note Object that has content and date.
 */
public class Note {
  private final UUID id;
  private String message;
  private final String date;

  /**
   * Constructor for Note with UUID initialization.
   */
  public Note(String message, String date) {
    this.id = UUID.randomUUID();
    this.message = message;
    this.date = date;
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