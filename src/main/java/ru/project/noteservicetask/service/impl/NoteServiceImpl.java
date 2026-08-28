package ru.project.noteservicetask.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.noteservicetask.dto.request.NoteRequest;
import ru.project.noteservicetask.dto.response.NoteResponse;
import ru.project.noteservicetask.entity.Note;
import ru.project.noteservicetask.exception.NoteNotFoundException;
import ru.project.noteservicetask.mapper.NoteMapper;
import ru.project.noteservicetask.repository.NoteRepository;
import ru.project.noteservicetask.service.NoteService;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

  private final NoteMapper noteMapper;

  private final NoteRepository noteRepository;

  @Override
  public NoteResponse createNote(NoteRequest request) {
    Note savedNote = noteRepository.create(noteMapper.toEntity(request));
    return noteMapper.toNoteResponse(savedNote);
  }

  @Override
  public NoteResponse findById(Long id) {
    Note note = noteRepository.findById(id)
        .orElseThrow(() -> new NoteNotFoundException(id));
    return noteMapper.toNoteResponse(note);
  }

  @Override
  public List<NoteResponse> getNotes(String tag) {
    List<Note> notes = (tag != null && !tag.isBlank())
        ? noteRepository.findByTag(tag)
        : noteRepository.findAll();
    return notes.stream().map(noteMapper::toNoteResponse).toList();
  }

  @Override
  public NoteResponse updateNote(Long id, NoteRequest request) {
    Note updatedNote = noteRepository.updateIfPresent(id, noteMapper.toEntity(request))
        .orElseThrow(() -> new NoteNotFoundException(id));
    return noteMapper.toNoteResponse(updatedNote);
  }

  @Override
  public void deleteNote(Long id) {
    if (!noteRepository.existsById(id)) {
      throw new NoteNotFoundException(id);
    }
    noteRepository.deleteById(id);
  }
}
