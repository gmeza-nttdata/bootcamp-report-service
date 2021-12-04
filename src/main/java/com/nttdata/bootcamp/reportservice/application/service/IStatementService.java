package com.nttdata.bootcamp.reportservice.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStatementService<T,Id> {

    Flux<T> getStatementsByNumber(Id number);
    Flux<T> getStatements();
}
