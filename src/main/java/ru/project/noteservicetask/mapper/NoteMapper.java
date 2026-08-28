package ru.project.noteservicetask.mapper;

import java.time.Instant;
import java.util.LinkedHashSet;
import org.springframework.stereotype.Component;
import ru.project.noteservicetask.dto.request.NoteRequest;
import ru.project.noteservicetask.dto.response.NoteResponse;
import ru.project.noteservicetask.entity.Note;

@Component
public class NoteMapper {

  public Note toEntity(NoteRequest request) {
    Note note = new Note();
    note.setTitle(request.title());
    note.setContent(request.content());
    note.setTags(request.tags() != null
        ? new LinkedHashSet<>(request.tags())
        : new LinkedHashSet<>());
    note.setCreatedAt(Instant.now());
    return note;
  }

  public NoteResponse toNoteResponse(Note entity) {
    return new NoteResponse(
        entity.getId(),
        entity.getTitle(),
        entity.getContent(),
        entity.getCreatedAt(),
        entity.getTags()
    );
  }

}
