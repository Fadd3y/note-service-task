package ru.project.noteservicetask.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.project.noteservicetask.dto.request.NoteRequest;
import ru.project.noteservicetask.dto.response.NoteResponse;
import ru.project.noteservicetask.service.NoteService;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

  private final NoteService noteService;

  @PostMapping()
  public ResponseEntity<NoteResponse> createNote(
      @RequestBody @Valid NoteRequest request) {
    return new ResponseEntity<>(noteService.createNote(request), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<NoteResponse> findById(
      @PathVariable Long id) {
    return ResponseEntity.ok(noteService.findById(id));
  }

  @GetMapping()
  public ResponseEntity<List<NoteResponse>> getNotes(
      @RequestParam(required = false) String tag) {
    return ResponseEntity.ok(noteService.getNotes(tag));
  }

  @PutMapping("/{id}")
  public ResponseEntity<NoteResponse> updateNote(
      @PathVariable Long id,
      @RequestBody @Valid NoteRequest request) {
    noteService.updateNote(id, request);
    return ResponseEntity.ok(noteService.updateNote(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotes(
      @PathVariable Long id) {
    noteService.deleteNote(id);
    return ResponseEntity.noContent().build();
  }

}
