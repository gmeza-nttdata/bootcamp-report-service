package com.nttdata.bootcamp.reportservice.infrastructure.service;

import com.nttdata.bootcamp.reportservice.application.service.CreditTransactionService;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.CreditTransactionStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditTransactionWebService implements CreditTransactionService {
    private final WebClient.Builder webClientBuilder;
    private final String URI;


    @Autowired
    public CreditTransactionWebService(WebClient.Builder webClientBuilder,
            @Value("${bootcamp.web.credit-transaction:http://credit-transaction-service/credit-transactions}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @Override
    public Flux<CreditTransactionStatement> getStatementsByNumber(String number) {
        return webClientBuilder.build().get().uri(URI + "/statements/{number}", number)
                .retrieve().bodyToFlux(CreditTransactionStatement.class);
    }

    @Override
    public Flux<CreditTransactionStatement> getStatements() {
        return webClientBuilder.build().get().uri(URI + "/statements")
                .retrieve().bodyToFlux(CreditTransactionStatement.class);
    }
}
