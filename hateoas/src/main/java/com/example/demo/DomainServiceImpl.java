package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public Domain getDomain() {

        Domain domain = new Domain(1L, "domain", "desc");
        return domain;
    }

    @Override
    public List<Domain> getDomains() {

        List<Domain> domains = new ArrayList<>();
        for (long i = 1; i < 10; i++) {
            domains.add(new Domain(i, "domain" + i, "desc" + i));
        }
        return domains;
    }

    @Override
    public Page<Domain> getPageDomains(int page) {

        //임의로 만듬
        Pageable pageable = PageRequest.of(page, 10);
        List<Domain> domains = new ArrayList<>();

        for (long i = 1; i < 10; i++) {
            domains.add(new Domain(i, "domain" + i, "desc" + i));
        }

        return new PageImpl<Domain>(domains, pageable, domains.size());
    }
}
