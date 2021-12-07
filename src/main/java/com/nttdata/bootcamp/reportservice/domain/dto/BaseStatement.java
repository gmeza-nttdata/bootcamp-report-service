package com.nttdata.bootcamp.reportservice.domain.dto;

import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.CreditTransactionStatement;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BaseStatement {
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private OperationType operationType;

    public BaseStatement() {}

    public BaseStatement(AccountTransactionStatement statement) {

        BigDecimal fee = statement.getFee();
        if (fee == null) fee = BigDecimal.ZERO;

        if (statement.getOperation().equals(OperationType.DEPOSIT.toString()))
            this.amount = statement.getAmount().subtract(fee);
        else
            this.amount = statement.getAmount().add(fee);

        this.dateTime = statement.getDateTime();
        this.operationType = OperationType.valueOf(statement.getOperation());
    }

    public BaseStatement(CreditTransactionStatement statement) {
        BigDecimal fee = statement.getFee();
        if (fee == null) fee = BigDecimal.ZERO;

        if (statement.getOperation().equals(OperationType.PAYMENT.toString()))
            this.amount = statement.getAmount().subtract(fee);
        else
            this.amount = statement.getAmount().add(fee);

        this.amount = statement.getAmount();
        this.dateTime = statement.getDateTime();
        this.operationType = OperationType.valueOf(statement.getOperation());
    }

}
