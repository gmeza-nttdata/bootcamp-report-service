package com.nttdata.bootcamp.reportservice.domain.entity.credit;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Credit {
    private String number;
    private Integer userId;
    private Boolean hasCard;
    private String currencyName;
    private BigDecimal balance;

    private BigDecimal creditLine;
    private BigDecimal rate;
    private Integer cutoffDate;
    private Integer paymentDate;
}
