package com.example.springliquibasedemo.modules.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping(value="users", produces = "application/hal+json")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> user(@PathVariable("id") Long id) throws UserNotFoundException {
        User user= userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id: " + id));
        return ResponseEntity.ok(new UserResource(user));
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody @Valid User user) {
        userRepository.save(user);
        return ResponseEntity
                .created(linkTo(UserController.class).slash(user.getId()).toUri())
                .body(new UserResource(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id: " + id));
        userRepository.delete(user);
        return ResponseEntity.ok(ResponseEntity.noContent().build());
    }
}
