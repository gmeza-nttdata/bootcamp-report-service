package com.nttdata.bootcamp.reportservice.application.service;

import com.nttdata.bootcamp.reportservice.domain.entity.credit.Credit;
import reactor.core.publisher.Flux;

public interface CreditService {
    Flux<Credit> getAll();
}
