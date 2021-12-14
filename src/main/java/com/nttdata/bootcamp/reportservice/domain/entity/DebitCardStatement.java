package com.nttdata.bootcamp.reportservice.domain.entity;

import com.nttdata.bootcamp.reportservice.domain.dto.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DebitCardStatement {
    private String id;
    private String productType;
    private String number;
    private OperationType operation;
    private String cardId;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private BigDecimal fee;
}
