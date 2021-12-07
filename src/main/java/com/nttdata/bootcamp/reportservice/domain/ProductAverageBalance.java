package com.nttdata.bootcamp.reportservice.domain;

import com.nttdata.bootcamp.reportservice.domain.entity.account.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAverageBalance {
    private String number;
    private String productType;
    private BigDecimal averageBalance;

    public ProductAverageBalance() {}

    public ProductAverageBalance(String number, String productType, BigDecimal balance) {
        this.averageBalance = balance;
        this.productType = productType;
        this.number = number;
    }
}
