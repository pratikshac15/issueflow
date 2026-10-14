package dev.issueflow.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
@Entity
@Table(name="issues")
public class Issue {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank @Size(min=3,max=120) @Column(nullable=false,length=120) private String title;
  @Size(max=2000) @Column(length=2000) private String description="";
  @Size(max=100) private String assignee="";
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Priority priority=Priority.MEDIUM;
  @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.OPEN;
  @Column(nullable=false,updatable=false) private Instant createdAt;
  @Column(nullable=false) private Instant updatedAt;
  @PrePersist void beforeInsert(){createdAt=Instant.now();updatedAt=createdAt;}
  @PreUpdate void beforeUpdate(){updatedAt=Instant.now();}
  public Long getId(){return id;}
  public String getTitle(){return title;} public void setTitle(String v){title=v;}
  public String getDescription(){return description;} public void setDescription(String v){description=v;}
  public String getAssignee(){return assignee;} public void setAssignee(String v){assignee=v;}
  public Priority getPriority(){return priority;} public void setPriority(Priority v){priority=v;}
  public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
  public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}
