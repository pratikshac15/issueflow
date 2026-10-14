package dev.issueflow.config;
import dev.issueflow.model.*;
import dev.issueflow.repo.IssueRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
@Configuration
public class SeedData {
  @Bean CommandLineRunner seed(IssueRepository repo){return args->{if(repo.count()>0)return;
    create(repo,"Review API documentation","Check example requests and error responses","Alex",Priority.HIGH,Status.OPEN);
    create(repo,"Add integration tests","Cover create and update endpoints","Sam",Priority.MEDIUM,Status.IN_PROGRESS);
    create(repo,"Polish dashboard","Improve issue filter usability","Taylor",Priority.LOW,Status.RESOLVED);
  };}
  private void create(IssueRepository repo,String title,String description,String assignee,Priority priority,Status status){Issue i=new Issue();i.setTitle(title);i.setDescription(description);i.setAssignee(assignee);i.setPriority(priority);i.setStatus(status);repo.save(i);}
}
