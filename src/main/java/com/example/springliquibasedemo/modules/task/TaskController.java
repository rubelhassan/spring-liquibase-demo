package com.example.springliquibasedemo.modules.task;

import com.example.springliquibasedemo.modules.user.UserNotFoundException;
import com.example.springliquibasedemo.modules.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.example.springliquibasedemo.modules.task.Task.TaskType.TODO;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/tasks", produces = "application/hal+json")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<Task>> all(Pageable pageable, PagedResourcesAssembler<Task> assembler) {
        return ResponseEntity.ok(taskRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> task(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        if (task.getType() == null) {
            task.setType(TODO);
        }
        taskRepository.save(task);
        return ResponseEntity
                .created(linkTo(TaskController.class).slash(task.getId()).toUri())
                .body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id,
                                           @RequestBody @Valid Task task) throws TaskNotFoundException, UserNotFoundException {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
        if (task.getUser() != null && !userRepository.existsById(task.getUser().getId())) {
            throw new UserNotFoundException("Invalid user provided to update task");
        }
        return ResponseEntity
                .created(linkTo(TaskController.class).slash(task.getId()).toUri())
                .body(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
        taskRepository.delete(task);
        return ResponseEntity.ok(ResponseEntity.noContent().build());
    }
}
