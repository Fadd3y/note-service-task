package ru.project.noteservicetask.service;

import java.util.List;
import ru.project.noteservicetask.dto.request.NoteRequest;
import ru.project.noteservicetask.dto.response.NoteResponse;

public interface NoteService {

  NoteResponse createNote(NoteRequest request);

  NoteResponse findById(Long id);

  List<NoteResponse> getNotes(String tag);

  NoteResponse updateNote(Long id, NoteRequest request);

  void deleteNote(Long id);

}
