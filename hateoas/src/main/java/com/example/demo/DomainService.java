package com.example.demo;

import org.springframework.data.domain.Page;

import java.util.List;

public interface DomainService {

    Domain getDomain();

    List<Domain> getDomains();

    Page<Domain> getPageDomains(int page);
}
