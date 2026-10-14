package dev.issueflow.repo;
import dev.issueflow.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IssueRepository extends JpaRepository<Issue,Long> {}
