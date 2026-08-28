package ru.project.noteservicetask.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import ru.project.noteservicetask.entity.Note;
import ru.project.noteservicetask.repository.NoteRepository;

@Repository
public class InMemoryNoteRepository implements NoteRepository {

  private final Map<Long, Note> notes = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(0);

  @Override
  public Note create(Note note) {
    if (note.getId() != null) {
      throw new IllegalArgumentException(
          "Use updateIfPresent() to update an existing note"
      );
    }

    Long id = idGenerator.incrementAndGet();
    note.setId(id);
    notes.put(id, note);

    return note;
  }

  @Override
  public Optional<Note> findById(Long id) {
    return Optional.ofNullable(notes.get(id));
  }

  @Override
  public List<Note> findAll() {
    return new ArrayList<>(notes.values());
  }

  @Override
  public List<Note> findByTag(String tag) {
    return notes.values().stream()
        .filter(note -> note.getTags() != null)
        .filter(note -> note.getTags().contains(tag))
        .toList();
  }

  @Override
  public Optional<Note> updateIfPresent(Long id, Note note) {
    Note updated = notes.computeIfPresent(id, (noteId, oldNote) -> {
      note.setCreatedAt(oldNote.getCreatedAt());
      note.setId(id);
      return note;
    });

    return Optional.ofNullable(updated);
  }

  @Override
  public void deleteById(Long id) {
    notes.remove(id);
  }

  @Override
  public boolean existsById(Long id) {
    return notes.containsKey(id);
  }
}
