package com.nttdata.bootcamp.reportservice.domain.entity.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    /** account number or id. */
    private String number;
    /** main holder. */
    private Integer userId;
    /** account type. */
    private String type;
    /** Currency of the account. */
    private String currencyName;
    /** Current Account balance. */
    private BigDecimal balance;

}
