package dev.issueflow.api;
import dev.issueflow.model.*;
import jakarta.validation.constraints.*;
public record IssueRequest(
  @NotBlank @Size(min=3,max=120) String title,
  @Size(max=2000) String description,
  @Size(max=100) String assignee,
  @NotNull Priority priority,
  @NotNull Status status
) {}
