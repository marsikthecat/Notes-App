package com.example.notelist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p> </p>
 * Notelist that stores Notes.
 */
public class Notelist implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private final HashMap<Integer, Note> noteHashMap;
  private int nodeIdCounter = 0;

  public Notelist() {
    this.noteHashMap = new HashMap<>();
  }

  public int addNote(Note note) {
    if (note == null) {
      throw new IllegalArgumentException("Cannot add null reference");
    } else {
      noteHashMap.put(nodeIdCounter, note);
      nodeIdCounter++;
    }
    return nodeIdCounter - 1;
  }

  public void removeNote(int id) {
    if (!noteHashMap.containsKey(id)) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    noteHashMap.remove(id);
    if (noteHashMap.isEmpty()) {
      nodeIdCounter = 0;
    }
  }

  public void editNote(int id, String newMessage) {
    if (!noteHashMap.containsKey(id)) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    Note note = noteHashMap.get(id);
    note.setMessage(newMessage);
  }

  public Note getNote(int id) {
    if (!noteHashMap.containsKey(id)) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    return noteHashMap.get(id);
  }

  public Set<Map.Entry<Integer, Note>> getNoteHashMap() {
    return noteHashMap.entrySet();
  }

  /***
   * <p></p>
   * Saves Note Objekts in the file.
  */
  public void saveNote() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("notes.ser"))) {
      out.writeObject(this);
    } catch (IOException i) {
      i.getCause();
    }
  }

  /***
   * <p> </p>
   * Lodes Notes from the File and deserialize them.
   */
  public static Notelist loadNote() {
    Notelist notelist = null;
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("notes.ser"))) {
      notelist = (Notelist) in.readObject();
    } catch (IOException | ClassNotFoundException i) {
      i.getCause();
    }
    return notelist;
  }
}
