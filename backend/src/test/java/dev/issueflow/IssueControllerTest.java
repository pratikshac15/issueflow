package dev.issueflow;
import dev.issueflow.repo.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class IssueControllerTest {
  @Autowired MockMvc mvc;
  @Autowired IssueRepository repository;
  @Test void listIssues() throws Exception {mvc.perform(get("/api/issues")).andExpect(status().isOk()).andExpect(jsonPath("$[0].title").exists());}
  @Test void createAndUpdateIssue() throws Exception {
    String body="{"title":"Test endpoint","description":"Created by test","assignee":"QA","priority":"HIGH","status":"OPEN"}";
    String json=mvc.perform(post("/api/issues").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isCreated()).andExpect(jsonPath("$.title").value("Test endpoint")).andReturn().getResponse().getContentAsString();
    long id=new com.fasterxml.jackson.databind.ObjectMapper().readTree(json).get("id").asLong();
    mvc.perform(put("/api/issues/"+id).contentType(MediaType.APPLICATION_JSON).content(body.replace("OPEN","RESOLVED"))).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("RESOLVED"));
    mvc.perform(delete("/api/issues/"+id)).andExpect(status().isNoContent());
  }
  @Test void rejectsInvalidIssue() throws Exception {mvc.perform(post("/api/issues").contentType(MediaType.APPLICATION_JSON).content("{"title":"x","priority":"HIGH","status":"OPEN"}")).andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").value("Validation failed"));}
  @Test void returnsNotFound() throws Exception {mvc.perform(get("/api/issues/999999")).andExpect(status().isNotFound());}
  @Test void health() throws Exception {mvc.perform(get("/api/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("ok"));}
}
