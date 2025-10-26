package com.example.notelist.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Notelist that stores Notes.
 */
public class Notelist {

  private HashMap<UUID, Note> noteHashMap;
  private final Gson gson = new Gson();

  /**
   * Constructor.
   */
  public Notelist() {
    this.noteHashMap = new HashMap<>();
  }

  /**
   * Adds a note to the Hashmap and increments the counter.
   */
  public void addNote(Note note) {
    if (note == null) {
      throw new IllegalArgumentException("Cannot add null reference");
    } else {
      noteHashMap.put(note.getId(), note);
    }
  }

  /**
   * Removes the Note that has the id.
   */
  public void removeNote(UUID id) {
    if (noteHashMap.remove(id) == null) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
  }

  /**
   * Edits a Note that has the corresponding id.
   */
  public void editNote(UUID id, String newMessage) {
    Note note = noteHashMap.get(id);
    if (note == null) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    note.setMessage(newMessage);
  }

  public HashMap<UUID, Note> getNoteHashMap() {
    return noteHashMap;
  }

  /**
   * Saves Note Objekts in the JSON file.
  */
  public void saveNote() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("notes.json"))) {
      gson.toJson(noteHashMap, writer);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Lodes Notes from the File. If the file does not exist, it will be created.
   */
  public void loadNote() {
    Path path = Path.of("notes.json");
    if (!Files.exists(path)) {
      try {
        Files.createFile(path);
        return;
      } catch (IOException e) {
        System.err.println(e.getMessage());
        return;
      }
    }
    try (BufferedReader reader = new BufferedReader(new FileReader("notes.json"))) {
      Type type = new TypeToken<Map<UUID, Note>>(){}.getType();
      noteHashMap = gson.fromJson(reader, type);
      if (noteHashMap == null) {
        noteHashMap = new HashMap<>();
      }
    } catch (IOException e) {
      noteHashMap = new HashMap<>();
      System.err.println(e.getMessage());
    }
  }
}