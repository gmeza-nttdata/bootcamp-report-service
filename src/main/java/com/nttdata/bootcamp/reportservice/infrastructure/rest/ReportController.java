package com.nttdata.bootcamp.reportservice.infrastructure.rest;

import com.nttdata.bootcamp.reportservice.application.ReportOperations;
import com.nttdata.bootcamp.reportservice.domain.FeeStatement;
import com.nttdata.bootcamp.reportservice.domain.ProductAverageBalance;
import com.nttdata.bootcamp.reportservice.domain.dto.DateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportOperations operations;

    @GetMapping(value = "average-balance/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<ProductAverageBalance>>> getAverageBalance(@PathVariable Integer id) {

        return Mono.just(
                ResponseEntity.ok(operations.generateSummaryWithDailyAverageBalanceOfThisMonth(id)
        ));

    }

    @PostMapping(value = "fee-report", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<FeeStatement>>> getFeeReport(@RequestBody DateDto dto ) {

        return Mono.just(
                ResponseEntity.ok(operations.generateFeeReportByProductInRangeOfTime(dto.getFrom(), dto.getTo()))
        );

    }

}
