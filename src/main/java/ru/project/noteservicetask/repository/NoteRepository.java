package ru.project.noteservicetask.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import org.springframework.stereotype.Repository;
import ru.project.noteservicetask.entity.Note;

public interface NoteRepository {

  Note create(Note note);

  Optional<Note> findById(Long id);

  List<Note> findAll();

  List<Note> findByTag(String tag);

  Optional<Note> updateIfPresent(Long id, Note note);

  void deleteById(Long id);

  boolean existsById(Long id);
}
