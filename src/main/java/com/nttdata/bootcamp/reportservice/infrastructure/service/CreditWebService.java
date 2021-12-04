package com.nttdata.bootcamp.reportservice.infrastructure.service;

import com.nttdata.bootcamp.reportservice.application.service.CreditService;
import com.nttdata.bootcamp.reportservice.domain.entity.credit.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditWebService implements CreditService {

    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public CreditWebService(WebClient.Builder webClientBuilder,
                            @Value("${bootcamp.web.credit: http://credit-service/accounts}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @Override
    public Flux<Credit> getAll() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(Credit.class);
    }
}