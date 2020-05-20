package com.example.demo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DomainRepresentationModelAssembler extends RepresentationModelAssemblerSupport<Domain, DomainModel> {

    public DomainRepresentationModelAssembler(Class<?> controllerClass, Class<DomainModel> resourceType) {
        super(controllerClass, resourceType);
    }

    public DomainRepresentationModelAssembler() {
        super(DomainController.class, DomainModel.class);
    }

    @Override
    public DomainModel toModel(Domain entity) {

        //DomainModel model = new DomainModel();
        //리플렉션을 통한 인스턴스한 객체로도 생성가능
        DomainModel model = instantiateModel(entity);

        //아래방식으로 이용하면 (self - /hateoas/{id}) link를 만들어준다.
        //DomainModel model2= createModelWithId(entity.getId(), entity);

        model.add(linkTo(methodOn(DomainController.class).basicHateoas()).withSelfRel())  // /hateoas/test1 - self
                .add(linkTo(DomainController.class).slash("test").withRel("test"))    // /hateoas/test - test
                .add(linkTo(methodOn(DomainController.class).dummy()).withRel("dummy"));     // /hateoas/dummy - dummy

        model.setId(entity.getId());
        model.setDesc(entity.getDesc());
        model.setName(entity.getName());
        return model;
    }

    @Override
    public CollectionModel<DomainModel> toCollectionModel(Iterable<? extends Domain> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(methodOn(DomainController.class).hateoasCollection()).withSelfRel());
    }
}
