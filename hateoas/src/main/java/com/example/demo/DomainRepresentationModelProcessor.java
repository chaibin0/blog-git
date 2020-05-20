package com.example.demo;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@Component
public class DomainRepresentationModelProcessor implements RepresentationModelProcessor<EntityModel<Domain>> {

    @Override
    public EntityModel<Domain> process(EntityModel<Domain> model) {
        return model.add(linkTo(methodOn(DomainController.class).basicHateoas()).withSelfRel())  // /hateoas/test1 - self
                .add(linkTo(DomainController.class).slash("test").withRel("test"))    // /hateoas/test - test
                .add(linkTo(methodOn(DomainController.class).dummy()).withRel("dummy"));     // /hateoas/dummy - dummy

    }
}
