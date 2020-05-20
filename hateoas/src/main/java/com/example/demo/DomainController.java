package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hatoeas")
public class DomainController {

    DomainService domainService;

    DomainRepresentationModelAssembler assembler;

    PagedResourcesAssembler pageAssembler;

    @Autowired
    public DomainController(DomainService domainService, DomainRepresentationModelAssembler assembler, PagedResourcesAssembler pageAssembler) {
        this.domainService = domainService;
        this.assembler = assembler;
        this.pageAssembler = pageAssembler;
    }

    @GetMapping("/dummy")
    public ResponseEntity<String> dummy() {

        return ResponseEntity.ok().body("dummy");
    }

    @GetMapping("{id}")
    public ResponseEntity<String> dummy2(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body("dummy" + id);
    }

    @GetMapping("/test1")
    public ResponseEntity<EntityModel<Domain>> basicHateoas() {

        Domain domain = domainService.getDomain();
        return ResponseEntity.ok().body(
                EntityModel.of(domain)
                        .add(linkTo(methodOn(DomainController.class).basicHateoas()).withSelfRel())  // /hateoas/test1 - self
                        .add(linkTo(DomainController.class).slash("test").withRel("test"))    // /hateoas/test - test
                        .add(linkTo(methodOn(DomainController.class).dummy()).withRel("dummy"))      // /hateoas/dummy - dummy
                        .add(linkTo(methodOn(DomainController.class).dummy2(1L)).withRel(IanaLinkRelations.TAG))  // /hateoas/1 - tag
        );
    }

    @GetMapping("/test2")
    public ResponseEntity<DomainModel> assemblerHateoas() {

        return ResponseEntity.ok().body(assembler.toModel(domainService.getDomain()));
    }

    @GetMapping("/test3")
    public ResponseEntity<EntityModel<Domain>> processorHateoas() {

        return ResponseEntity.ok().body(EntityModel.of(domainService.getDomain()));
    }

    @GetMapping("/collection/test1")
    public ResponseEntity<CollectionModel<EntityModel<Domain>>> hateoasCollection() {
        List<EntityModel<Domain>> domains = domainService.getDomains().
                stream()
                .map((domain) ->
                        EntityModel.of(domain)
                                .add(linkTo(methodOn(DomainController.class).basicHateoas()).withSelfRel())  // /hateoas/test1 - self
                                .add(linkTo(DomainController.class).slash("test").withRel("test"))    // /hateoas/test - test
                                .add(linkTo(methodOn(DomainController.class).dummy()).withRel("dummy"))      // /hateoas/dummy - dummy
                                .add(linkTo(methodOn(DomainController.class).dummy2(1L)).withRel(IanaLinkRelations.TAG))  // /hateoas/1 - tag
                ).collect(Collectors.toList());

        return ResponseEntity.ok().body(CollectionModel.of(domains)
                .add(linkTo(methodOn(DomainController.class).hateoasCollection()).withSelfRel())
        );
    }

    @GetMapping("/collection/test2")
    public ResponseEntity<CollectionModel<DomainModel>> assemblerHateoasCollection() {
        return ResponseEntity.ok().body(assembler.toCollectionModel(domainService.getDomains()));
    }

    @GetMapping("/page/test1")
    public ResponseEntity<PagedModel<Domain>> hateoasPageModel(@RequestParam("page") int page) {
        return ResponseEntity.ok().body(pageAssembler.toModel(domainService.getPageDomains(page), assembler));
    }
}
