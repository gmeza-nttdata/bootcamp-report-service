package com.nttdata.bootcamp.reportservice.domain.dto;

import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransferStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.CreditTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.DebitCardStatement;
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

    public BaseStatement(DebitCardStatement statement) {

        BigDecimal fee = statement.getFee();
        if (fee == null) fee = BigDecimal.ZERO;

        if (statement.getOperation().equals(OperationType.DEPOSIT))
            this.amount = statement.getAmount().subtract(fee);
        else
            this.amount = statement.getAmount().add(fee);

        this.dateTime = statement.getDateTime();
        this.operationType = statement.getOperation();
    }

    public BaseStatement(AccountTransferStatement statement, String number) {

        BigDecimal fee = statement.getFee();
        if (fee == null) fee = BigDecimal.ZERO;

        if (statement.getTargetNumber().equals(number)) {
            // Case: DEPOSIT
            this.operationType = OperationType.DEPOSIT;
            this.amount = statement.getAmount().subtract(fee);
        }
        else {
            // CASE: WITHDRAWAL
            this.operationType = OperationType.WITHDRAWAL;
            this.amount = statement.getAmount().add(fee);
        }

        this.dateTime = statement.getDateTime();
    }



    public BaseStatement(CreditTransactionStatement statement) {
        BigDecimal fee = statement.getFee();
        if (fee == null) fee = BigDecimal.ZERO;

        if (statement.getOperation().equals(OperationType.CREDIT_PAYMENT.toString()))
            this.amount = statement.getAmount().subtract(fee);
        else
            this.amount = statement.getAmount().add(fee);

        this.amount = statement.getAmount();
        this.dateTime = statement.getDateTime();
        this.operationType = OperationType.valueOf(statement.getOperation());
    }

}
