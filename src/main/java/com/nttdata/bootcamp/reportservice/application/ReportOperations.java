package com.nttdata.bootcamp.reportservice.application;

import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductAverageBalance;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

public interface ReportOperations {

    Flux<ProductAverageBalance>generateSummaryWithDailyAverageBalanceOfThisMonth(Integer userId);
    Flux<FeeStatement> generateFeeReportByProductInRangeOfTime(LocalDate from, LocalDate to);

}
