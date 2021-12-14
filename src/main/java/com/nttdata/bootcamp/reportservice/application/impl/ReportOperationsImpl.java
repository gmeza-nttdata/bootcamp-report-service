package com.nttdata.bootcamp.reportservice.application.impl;

import com.nttdata.bootcamp.reportservice.application.ReportOperations;
import com.nttdata.bootcamp.reportservice.application.service.*;
import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductAverageBalance;
import com.nttdata.bootcamp.reportservice.domain.dto.AccountReportDto;
import com.nttdata.bootcamp.reportservice.domain.dto.BaseStatement;
import com.nttdata.bootcamp.reportservice.domain.dto.CreditReportDto;
import com.nttdata.bootcamp.reportservice.domain.dto.ProductType;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransferStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.CreditTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.DebitCardStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.account.Account;
import com.nttdata.bootcamp.reportservice.domain.entity.credit.Credit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportOperationsImpl implements ReportOperations {

    private final AccountTransactionService accountTransactionService;
    private final AccountTransferService accountTransferService;
    private final CreditTransactionService creditTransactionService;
    private final DebitCardStatementService debitCardStatementService;
    private final AccountService accountService;
    private final CreditService creditService;


    @Override
    public Flux<ProductAverageBalance> generateSummaryWithDailyAverageBalanceOfThisMonth(Integer userId) {

        // Get User accounts and credits first
        Flux<ProductAverageBalance> accountFlux = accountService.getAll()
                .filter(account -> account.getUserId().equals(userId))
                .flatMap(account -> Flux.merge(
                        accountTransactionService.getStatementsByNumber(account.getNumber())
                                .map(BaseStatement::new),
                        accountTransferService.getStatementsByNumber(account.getNumber())
                                .map(s -> new BaseStatement(s, account.getNumber())),
                        debitCardStatementService.getStatementsByNumber(account.getNumber())
                                .map(BaseStatement::new))
                                .filter(this::filterCurrentMonth)
                                .collectList()
                                .map(statements -> this.computeProductAverageBalance(statements, account)));

        Flux<ProductAverageBalance> creditFlux = creditService.getAll()
                .filter(credit -> credit.getUserId().equals(userId))
                .flatMap(credit -> creditTransactionService.getStatementsByNumber(credit.getNumber())
                        .map(BaseStatement::new)
                        .filter(this::filterCurrentMonth)
                        .collectList()
                        .map(statements -> this.computeProductAverageBalance(statements, credit)));

        return Flux.merge(accountFlux, creditFlux);
    }

    @Override
    public Mono<HashMap<String, BigDecimal>> generateFeeReportByProductInRangeOfTime(LocalDate from, LocalDate to) {
        return Mono.zip(
                accountTransactionService.getStatements()
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .map(AccountTransactionStatement::getFee)
                        .doOnNext(f -> f = (f==null)?BigDecimal.ZERO:f)
                        .reduce(BigDecimal::add),
                accountTransferService.getStatements()
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .map(AccountTransferStatement::getFee)
                        .doOnNext(f -> f = (f==null)?BigDecimal.ZERO:f)
                        .reduce(BigDecimal::add),
                debitCardStatementService.getStatements()
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .map(DebitCardStatement::getFee)
                        .doOnNext(f -> f = (f==null)?BigDecimal.ZERO:f)
                        .reduce(BigDecimal::add),
                creditTransactionService.getStatements()
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .map(CreditTransactionStatement::getFee)
                        .doOnNext(f -> f = (f==null)?BigDecimal.ZERO:f)
                        .reduce(BigDecimal::add))
                .map(tuple -> {
                            HashMap<String, BigDecimal> map = new HashMap<>();
                            map.put("Account", tuple.getT1().add(tuple.getT2()));
                            map.put("Debit Card", tuple.getT3());
                            map.put("Credit Card", tuple.getT4());
                            return map;
                        }
                );
    }

    @Override
    public Mono<AccountReportDto> generateAccountReport(String id, LocalDate from, LocalDate to) {
        return Mono.zip(
                accountTransactionService.getStatementsByNumber(id)
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .collectList(),
                debitCardStatementService.getStatementsByNumber(id)
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .collectList(),
                accountTransferService.getStatementsByNumber(id)
                        .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                        .collectList(),
                accountService.get(id)
        )
                .map(tuple -> new AccountReportDto(tuple.getT4(), tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    @Override
    public Mono<CreditReportDto> generateCreditReport(String id, LocalDate from, LocalDate to) {
        return creditService.get(id)
                .flatMap(credit ->
                    creditTransactionService.getStatementsByNumber(id)
                            .filter(s -> filterStatementByDateRange(s.getDateTime(), from, to))
                            .collectList()
                            .map(statements -> new CreditReportDto(credit, statements))
                );
    }

    private boolean filterStatementByDateRange(LocalDateTime dateTime, LocalDate from, LocalDate to) {
        LocalDate date = dateTime.toLocalDate();
        return date.equals(from) || date.equals(to) || date.isAfter(from) || date.isBefore(to);

    }

    private ProductAverageBalance computeProductAverageBalance
            (List<BaseStatement> statements, Account account) {
        return computeProductAverageBalance(statements, account.getNumber(),
                ProductType.ACCOUNT + ": " + account.getType(),
                account.getBalance());
    }

    private ProductAverageBalance computeProductAverageBalance
            (List<BaseStatement> statements, Credit credit) {
        return computeProductAverageBalance(statements, credit.getNumber(),
                ProductType.CREDIT.toString(),
                credit.getBalance());
    }

    private ProductAverageBalance computeProductAverageBalance
            (List<BaseStatement> statements, String number,
             String productType, BigDecimal currentBalance) {


        if (statements.isEmpty())
            return new ProductAverageBalance(number, productType, currentBalance);

        LocalDate end = LocalDate.now(),
                start = LocalDate.of(end.getYear(), end.getMonth(), 1);

        // Each index corresponds to a day - 1
        List<BigDecimal> operationList = Stream.iterate(0, num -> 0)
                .limit(ChronoUnit.DAYS.between(start, end) + 1)
                .map(BigDecimal::valueOf).collect(Collectors.toList()),
                balanceList = new ArrayList<>(operationList);

        // Populate Operations list
        statements.forEach(statement -> {
            int idx = statement.getDateTime().getDayOfMonth() - 1;
            BigDecimal partialBalance;
            switch (statement.getOperationType()) {
                case CREDIT_PAYMENT:
                case DEPOSIT:
                    partialBalance = operationList.get(idx).add(statement.getAmount());
                    break;
                case PAYMENT:
                case WITHDRAWAL:
                    partialBalance = operationList.get(idx).subtract(statement.getAmount());
                    break;
                default:
                    log.error("Invalid Operation for: " + statement.toString());
                    throw new IllegalArgumentException("Invalid OperationType in: "
                            + statement.toString());
            }
            operationList.set(idx, partialBalance);
        });

        // Populate Balance list
        balanceList.set(balanceList.size() - 1, currentBalance);
        for (int i = balanceList.size() - 1; i > 0;  i--)
            balanceList.set(i - 1, balanceList.get(i).subtract(operationList.get(i)));

        BigDecimal averageBalance = balanceList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(balanceList.size()), RoundingMode.UNNECESSARY);
        return new ProductAverageBalance(number, productType, averageBalance);
    }

    private boolean filterCurrentMonth(BaseStatement statement) {
        LocalDateTime dateTime = statement.getDateTime();
        return dateTime.isAfter(LocalDateTime.of(
                LocalDate.of(dateTime.getYear(), dateTime.getMonth(), 1),
                LocalTime.of(0,0,0)
        ));
    }


}
