package com.nttdata.bootcamp.reportservice.application.service;

import com.nttdata.bootcamp.reportservice.domain.entity.account.Account;
import reactor.core.publisher.Flux;

public interface AccountService {
    Flux<Account> getAll();
}
