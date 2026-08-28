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
  private final Map<String, Set<Long>> tagIndex = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong(0);

  @Override
  public Note create(Note note) {
    if (note.getId() != null) {
      throw new IllegalArgumentException("Use updateIfPresent() to update an existing note");
    }
    Long id = idGenerator.incrementAndGet();
    note.setId(id);
    notes.put(id, note);
    addToTagIndex(note);
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
    Set<Long> ids = tagIndex.getOrDefault(tag, Collections.emptySet());
    return ids.stream()
        .map(notes::get)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Note> updateIfPresent(Long id, Note note) {
    Note updated = notes.computeIfPresent(id, (noteId, oldNote) -> {
      note.setCreatedAt(oldNote.getCreatedAt());
      note.setId(id);
      removeFromTagIndex(oldNote);
      addToTagIndex(note);
      return note;
    });
    return Optional.ofNullable(updated);
  }

  @Override
  public void deleteById(Long id) {
    Note removed = notes.remove(id);
    if (removed != null) {
      removeFromTagIndex(removed);
    }
  }

  @Override
  public boolean existsById(Long id) {
    return notes.containsKey(id);
  }

  private void removeFromTagIndex(Note note) {
    if (note == null || note.getTags() == null) return;

    for (String tag : note.getTags()) {
      Set<Long> ids = tagIndex.get(tag);
      if (ids != null) {
        ids.remove(note.getId());
        if (ids.isEmpty()) {
          tagIndex.remove(tag);
        }
      }
    }
  }

  private void addToTagIndex(Note note) {
    if (note.getTags() == null) return;

    for (String tag : note.getTags()) {
      tagIndex.computeIfAbsent(tag, t -> ConcurrentHashMap.newKeySet())
          .add(note.getId());
    }
  }
}
