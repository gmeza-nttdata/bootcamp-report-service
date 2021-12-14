package com.nttdata.bootcamp.reportservice.application.service;

import com.nttdata.bootcamp.reportservice.domain.entity.account.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<Account> get(String id);
    Flux<Account> getAll();
}
