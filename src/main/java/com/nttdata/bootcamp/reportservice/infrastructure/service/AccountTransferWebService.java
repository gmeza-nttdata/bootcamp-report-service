package com.nttdata.bootcamp.reportservice.infrastructure.service;

import com.nttdata.bootcamp.reportservice.application.service.AccountTransferService;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransferStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class AccountTransferWebService implements AccountTransferService {
    private final WebClient.Builder webClientBuilder;
    private final String URI;


    @Autowired
    public AccountTransferWebService(WebClient.Builder webClientBuilder,
            @Value("${bootcamp.web.account-transfer:http://account-transfer/account-transfers}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @Override
    public Flux<AccountTransferStatement> getStatementsByNumber(String number) {
        return webClientBuilder.build().get().uri(URI + "/statements/{number}", number)
                .retrieve().bodyToFlux(AccountTransferStatement.class);
    }

    @Override
    public Flux<AccountTransferStatement> getStatements() {
        return webClientBuilder.build().get().uri(URI + "/statements")
                .retrieve().bodyToFlux(AccountTransferStatement.class);
    }
}
