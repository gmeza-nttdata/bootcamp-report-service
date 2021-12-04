package com.nttdata.bootcamp.reportservice.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountTransferStatement {
    private String id;
    private String sourceNumber;
    private String targetNumber;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;
}
