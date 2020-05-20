package com.example.demo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Domain {

    Long id;
    String name;
    String desc;

    public Domain(Long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
