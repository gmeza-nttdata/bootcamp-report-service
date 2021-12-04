package com.nttdata.bootcamp.reportservice.application.impl;

import com.nttdata.bootcamp.reportservice.application.ReportOperations;
import com.nttdata.bootcamp.reportservice.application.service.AccountTransactionService;
import com.nttdata.bootcamp.reportservice.application.service.AccountTransferService;
import com.nttdata.bootcamp.reportservice.application.service.CreditTransactionService;
import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductBalance;
import com.nttdata.bootcamp.reportservice.infrastructure.service.AccountWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportOperationsImpl implements ReportOperations {

    private final AccountTransactionService accountTransactionService;
    private final AccountTransferService accountTransferService;
    private final CreditTransactionService creditTransactionService;
    private final AccountWebService accountWebService;


    @Override
    public Flux<ProductBalance> generateSummaryWithAverageDailyBalanceOfThisMonth(Integer userId) {
        return null;
    }

    @Override
    public Flux<FeeStatement> generateFeeReportByProductInRangeOfTime(LocalDate from, LocalDate to) {
        return null;
    }
}
