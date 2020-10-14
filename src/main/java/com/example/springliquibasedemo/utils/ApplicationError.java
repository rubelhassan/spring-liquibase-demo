package com.example.springliquibasedemo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "error")
@AllArgsConstructor
@Getter
public class ApplicationError extends RepresentationModel<ApplicationError> {
    private final String message;
}
