package com.nttdata.bootcamp.reportservice.application;

import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductAverageBalance;
import com.nttdata.bootcamp.reportservice.domain.dto.AccountReportDto;
import com.nttdata.bootcamp.reportservice.domain.dto.CreditReportDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public interface ReportOperations {

    // Bank
    Flux<ProductAverageBalance>generateSummaryWithDailyAverageBalanceOfThisMonth(Integer userId);
    Mono<HashMap<String, BigDecimal>> generateFeeReportByProductInRangeOfTime(LocalDate from, LocalDate to);

    // User
    Mono<AccountReportDto> generateAccountReport(String id, LocalDate from, LocalDate to);
    Mono<CreditReportDto> generateCreditReport(String id, LocalDate from, LocalDate to);

}
