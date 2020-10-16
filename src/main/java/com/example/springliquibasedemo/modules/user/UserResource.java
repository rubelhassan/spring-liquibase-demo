package com.example.springliquibasedemo.modules.user;

import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class UserResource extends RepresentationModel<UserResource> {
    private final User user;

    public UserResource(User user) {
        this.user = user;
        add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }
}
