package ru.project.noteservicetask.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.project.noteservicetask.dto.request.NoteRequest;
import ru.project.noteservicetask.dto.response.NoteResponse;
import ru.project.noteservicetask.entity.Note;
import ru.project.noteservicetask.exception.NoteNotFoundException;
import ru.project.noteservicetask.mapper.NoteMapper;
import ru.project.noteservicetask.repository.NoteRepository;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

  @Mock
  private NoteMapper noteMapper;

  @Mock
  private NoteRepository noteRepository;

  @InjectMocks
  private NoteServiceImpl noteService;

  private Note note;
  private NoteRequest request;
  private NoteResponse response;

  @BeforeEach
  void setUp() {
    note = new Note(1L, "Title", "Content", Instant.now(), Set.of("work"));
    request = new NoteRequest("Title", "Content", Set.of("work"));
    response = new NoteResponse(1L, "Title", "Content", note.getCreatedAt(), Set.of("work"));
  }

  @Test
  void createNote_shouldSaveAndReturnResponse() {
    when(noteMapper.toEntity(request)).thenReturn(note);
    when(noteRepository.create(note)).thenReturn(note);
    when(noteMapper.toNoteResponse(note)).thenReturn(response);

    NoteResponse result = noteService.createNote(request);

    assertThat(result).isEqualTo(response);
    verify(noteRepository).create(note);
  }

  @Test
  void findById_shouldReturnResponse_whenNoteExists() {
    when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
    when(noteMapper.toNoteResponse(note)).thenReturn(response);

    NoteResponse result = noteService.findById(1L);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void findById_shouldThrow_whenNoteNotFound() {
    when(noteRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> noteService.findById(99L))
        .isInstanceOf(NoteNotFoundException.class);

    verify(noteMapper, never()).toNoteResponse(any());
  }

  @Test
  void getNotes_shouldCallFindAll_whenTagIsNull() {
    when(noteRepository.findAll()).thenReturn(List.of(note));
    when(noteMapper.toNoteResponse(note)).thenReturn(response);

    List<NoteResponse> result = noteService.getNotes(null);

    assertThat(result).containsExactly(response);
    verify(noteRepository).findAll();
    verify(noteRepository, never()).findByTag(any());
  }

  @Test
  void getNotes_shouldCallFindByTag_whenTagProvided() {
    when(noteRepository.findByTag("work")).thenReturn(List.of(note));
    when(noteMapper.toNoteResponse(note)).thenReturn(response);

    List<NoteResponse> result = noteService.getNotes("work");

    assertThat(result).containsExactly(response);
    verify(noteRepository).findByTag("work");
    verify(noteRepository, never()).findAll();
  }

  @Test
  void updateNote_shouldReturnUpdatedResponse_whenNoteExists() {
    when(noteMapper.toEntity(request)).thenReturn(note);
    when(noteRepository.updateIfPresent(eq(1L), eq(note))).thenReturn(Optional.of(note));
    when(noteMapper.toNoteResponse(note)).thenReturn(response);

    NoteResponse result = noteService.updateNote(1L, request);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void updateNote_shouldThrow_whenNoteNotFound() {
    when(noteMapper.toEntity(request)).thenReturn(note);
    when(noteRepository.updateIfPresent(eq(99L), eq(note))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> noteService.updateNote(99L, request))
        .isInstanceOf(NoteNotFoundException.class);
  }

  @Test
  void deleteNote_shouldDelete_whenNoteExists() {
    when(noteRepository.existsById(1L)).thenReturn(true);

    noteService.deleteNote(1L);

    verify(noteRepository, times(1)).deleteById(1L);
  }

  @Test
  void deleteNote_shouldThrow_whenNoteNotFound() {
    when(noteRepository.existsById(99L)).thenReturn(false);

    assertThatThrownBy(() -> noteService.deleteNote(99L))
        .isInstanceOf(NoteNotFoundException.class);

    verify(noteRepository, never()).deleteById(anyLong());
  }
}