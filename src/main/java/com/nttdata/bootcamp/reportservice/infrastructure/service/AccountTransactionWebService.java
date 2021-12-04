package com.nttdata.bootcamp.reportservice.infrastructure.service;

import com.nttdata.bootcamp.reportservice.application.service.AccountTransactionService;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class AccountTransactionWebService implements AccountTransactionService {
    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public AccountTransactionWebService(WebClient.Builder webClientBuilder,
             @Value("${bootcamp.web.account-transaction:http://account-transaction/account-transactions}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }


    @Override
    public Flux<AccountTransactionStatement> getStatementsByNumber(String number) {
        return webClientBuilder.build().get().uri(URI + "/statements/{number}", number)
                .retrieve().bodyToFlux(AccountTransactionStatement.class);
    }

    @Override
    public Flux<AccountTransactionStatement> getStatements() {
        return webClientBuilder.build().get().uri(URI + "/statements")
                .retrieve().bodyToFlux(AccountTransactionStatement.class);
    }
}
