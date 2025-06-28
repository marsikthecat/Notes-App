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
import java.util.UUID;

/**
 * Notelist that stores Notes.
 */
public class Notelist implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private final HashMap<UUID, Note> noteHashMap;

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
    if (noteHashMap.containsKey(id)) {
      noteHashMap.remove(id);
    } else {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
  }

  /**
   * Edits a Note with the corresponding id.
   */

  public void editNote(UUID id, String newMessage) {
    if (noteHashMap.containsKey(id)) {
      noteHashMap.get(id).setMessage(newMessage);
    } else {
      throw new IllegalArgumentException("No note with id: " + id + " found");
    }
  }

  public Set<Map.Entry<UUID, Note>> getNoteHashMap() {
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