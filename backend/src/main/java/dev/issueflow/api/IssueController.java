package dev.issueflow.api;
import dev.issueflow.model.*;
import dev.issueflow.repo.IssueRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.*;
@RestController
@RequestMapping("/api/issues")
public class IssueController {
  private final IssueRepository repository;
  public IssueController(IssueRepository repository){this.repository=repository;}
  @GetMapping public List<Issue> all(@RequestParam(required=false) Status status,@RequestParam(required=false) String q){
    String needle=q==null?"":q.trim().toLowerCase(Locale.ROOT);
    return repository.findAll().stream()
      .filter(i->status==null||i.getStatus()==status)
      .filter(i->needle.isEmpty()||i.getTitle().toLowerCase(Locale.ROOT).contains(needle)||
        (i.getDescription()!=null&&i.getDescription().toLowerCase(Locale.ROOT).contains(needle)))
      .sorted(Comparator.comparing(Issue::getCreatedAt).reversed()).collect(Collectors.toList());
  }
  @GetMapping("/{id}") public Issue get(@PathVariable Long id){return find(id);}
  @PostMapping @ResponseStatus(HttpStatus.CREATED) public Issue create(@Valid @RequestBody IssueRequest req){return repository.save(assign(new Issue(),req));}
  @PutMapping("/{id}") public Issue update(@PathVariable Long id,@Valid @RequestBody IssueRequest req){return repository.save(assign(find(id),req));}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repository.delete(find(id));}
  private Issue find(Long id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Issue not found"));}
  private Issue assign(Issue i,IssueRequest r){i.setTitle(r.title().trim());i.setDescription(r.description()==null?"":r.description());i.setAssignee(r.assignee()==null?"":r.assignee());i.setPriority(r.priority());i.setStatus(r.status());return i;}
}
