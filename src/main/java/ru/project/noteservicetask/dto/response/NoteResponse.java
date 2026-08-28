package ru.project.noteservicetask.dto.response;

import java.time.Instant;
import java.util.Set;

public record NoteResponse(
    long id,
    String title,
    String content,
    Instant createdAt,
    Set<String> tags
) {

}
