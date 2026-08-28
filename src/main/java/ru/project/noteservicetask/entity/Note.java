package ru.project.noteservicetask.entity;

import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  private Long id;

  private String title;

  private String content;

  private Instant createdAt;

  private Set<String> tags;

}
