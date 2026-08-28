package ru.project.noteservicetask.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record NoteRequest(
    @NotBlank
    String title,

    String content,

    Set<String> tags
) {

}
