package com.nttdata.bootcamp.reportservice.application.service;

import com.nttdata.bootcamp.reportservice.domain.entity.credit.Credit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
    Mono<Credit> get(String id);
    Flux<Credit> getAll();
}
