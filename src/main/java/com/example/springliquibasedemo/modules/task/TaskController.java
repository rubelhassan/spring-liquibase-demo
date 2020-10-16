package com.example.springliquibasedemo.modules.task;

import com.example.springliquibasedemo.modules.user.UserNotFoundException;
import com.example.springliquibasedemo.modules.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.springliquibasedemo.modules.task.Task.TaskType.TODO;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/tasks", produces = "application/hal+json")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<TaskResource>>> all(Pageable pageable,
                                                                     PagedResourcesAssembler<TaskResource> assembler) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(tasks.map(TaskResource::new)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResource> task(@PathVariable("id") Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
        return ResponseEntity.ok(new TaskResource(task));
    }

    @PostMapping
    public ResponseEntity<TaskResource> createTask(@RequestBody @Valid Task task) {
        if (task.getType() == null) {
            task.setType(TODO);
        }
        if (task.getUser() != null ) {
            task.setUser(userRepository.findById(task.getUser().getId()).orElse(null));
        }
        taskRepository.save(task);
        return ResponseEntity
                .created(linkTo(TaskController.class).slash(task.getId()).toUri())
                .body(new TaskResource(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResource> updateTask(@PathVariable("id") Long id,
                                                   @RequestBody @Valid Task task) throws TaskNotFoundException, UserNotFoundException {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
        if (task.getUser() != null ) {
            task.setUser(userRepository.findById(task.getUser().getId())
                    .orElseThrow(() -> new UserNotFoundException("Invalid user provided to update task")));
        }
        taskRepository.save(task);
        return ResponseEntity
                .created(linkTo(TaskController.class).slash(task.getId()).toUri())
                .body(new TaskResource(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("No task found with id: " + id));
        taskRepository.delete(task);
        return ResponseEntity.ok(ResponseEntity.noContent().build());
    }
}
