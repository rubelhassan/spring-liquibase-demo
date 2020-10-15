package com.example.springliquibasedemo.modules.task;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Getter
@Relation(value = "task", collectionRelation = "tasks")
public class TaskResource extends RepresentationModel<TaskResource> {
    private final Task task;

    public TaskResource(Task task) {
        this.task = task;
        add(linkTo(TaskController.class).withRel("tasks"));
        add(linkTo(TaskController.class).slash(task.getId()).withSelfRel());
    }
}
