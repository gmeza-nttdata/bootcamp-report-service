package com.nttdata.bootcamp.reportservice.infrastructure.service;

import com.nttdata.bootcamp.reportservice.application.service.DebitCardStatementService;
import com.nttdata.bootcamp.reportservice.domain.entity.DebitCardStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class DebitCardStatementWebService implements DebitCardStatementService {
    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public DebitCardStatementWebService(WebClient.Builder webClientBuilder,
                                        @Value("${bootcamp.web.debit-card-statement:http://debit-card-statement-service/debit-card-statements}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }


    @Override
    public Flux<DebitCardStatement> getStatementsByNumber(String number) {
        return webClientBuilder.build().get().uri(URI + "/{number}", number)
                .retrieve().bodyToFlux(DebitCardStatement.class);
    }

    @Override
    public Flux<DebitCardStatement> getStatements() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(DebitCardStatement.class);
    }
}
