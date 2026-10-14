package dev.issueflow.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@RestControllerAdvice
public class ApiErrorHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String,Object>> invalid(MethodArgumentNotValidException ex){
    List<String> errors=ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).toList();
    return ResponseEntity.badRequest().body(Map.of("error","Validation failed","details",errors));
  }
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String,String>> missing(ResponseStatusException ex){
    return ResponseEntity.status(ex.getStatusCode()).body(Map.of("error",Objects.toString(ex.getReason(),"Request failed")));
  }
}
