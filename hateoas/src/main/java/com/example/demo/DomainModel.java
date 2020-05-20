package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
public class DomainModel extends RepresentationModel<DomainModel> {

    Long id;
    String name;
    String desc;

    public DomainModel() {
        super();
    }

}
