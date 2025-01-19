package com.example.notelist.model;

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
 * Notelist that stores Notes.
 */
public class Notelist implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private final HashMap<Integer, Note> noteHashMap;
  private int nodeIdCounter = 0;

  /**
   * Constructor.
   */

  public Notelist() {
    this.noteHashMap = new HashMap<>();
  }

  /**
   * Adds a note to the Hashmap and increments the counter.
   */
  public int addNote(Note note) {
    if (note == null) {
      throw new IllegalArgumentException("Cannot add null reference");
    } else {
      noteHashMap.put(nodeIdCounter, note);
      nodeIdCounter++;
    }
    return nodeIdCounter - 1;
  }

  /**
   * Removes the Note that has the id. If no Note exist the counter resets to zero.
   */

  public void removeNote(int id) {
    if (!noteHashMap.containsKey(id)) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    noteHashMap.remove(id);
    if (noteHashMap.isEmpty()) {
      nodeIdCounter = 0;
    }
  }

  /**
   * Edits a Note with the corresponding id.
   */

  public void editNote(int id, String newMessage) {
    if (!noteHashMap.containsKey(id)) {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
    Note note = noteHashMap.get(id);
    note.setMessage(newMessage);
  }

  public Set<Map.Entry<Integer, Note>> getNoteHashMap() {
    return noteHashMap.entrySet();
  }

  /**
   * Saves Note Objekts in the file.
  */
  public void saveNote() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("notes.ser"))) {
      out.writeObject(this);
    } catch (IOException i) {
      i.getCause();
    }
  }

  /**
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