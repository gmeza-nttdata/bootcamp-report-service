package com.nttdata.bootcamp.reportservice.application;

import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductBalance;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ReportOperations {

    Flux<ProductBalance> generateSummaryWithAverageDailyBalanceOfThisMonth(Integer userId);
    Flux<FeeStatement> generateFeeReportByProductInRangeOfTime(LocalDate from, LocalDate to);

}
