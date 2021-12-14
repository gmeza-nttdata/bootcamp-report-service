package com.nttdata.bootcamp.reportservice.domain.dto;

import com.nttdata.bootcamp.reportservice.domain.entity.CreditTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.credit.Credit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreditReportDto {

    private String number;
    private Integer userId;
    private Boolean hasCard;
    private String currencyName;
    private BigDecimal balance;

    private BigDecimal creditLine;
    private BigDecimal rate;
    private Integer cutoffDate;
    private Integer paymentDate;

    private List<CreditTransactionStatement> statements;

    public CreditReportDto(Credit credit, List<CreditTransactionStatement> statements) {
        BeanUtils.copyProperties(credit, this);
        this.statements = statements;
    }
}
