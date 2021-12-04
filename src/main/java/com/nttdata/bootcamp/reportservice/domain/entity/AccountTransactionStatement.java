package com.nttdata.bootcamp.reportservice.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountTransactionStatement {
    private String id;
    private String productType;
    private String number;
    private String operation;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;
}
