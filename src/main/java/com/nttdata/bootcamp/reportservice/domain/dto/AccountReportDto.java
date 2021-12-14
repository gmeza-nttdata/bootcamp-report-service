package com.nttdata.bootcamp.reportservice.domain.dto;

import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransactionStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.AccountTransferStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.DebitCardStatement;
import com.nttdata.bootcamp.reportservice.domain.entity.account.Account;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountReportDto {

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

    private List<AccountTransactionStatement> accountTransactionStatements;
    private List<DebitCardStatement> debitCardStatements;
    private List<AccountTransferStatement> accountTransferStatements;


    public AccountReportDto(Account t4, List<AccountTransactionStatement> t1, List<DebitCardStatement> t2, List<AccountTransferStatement> t3) {
        BeanUtils.copyProperties(t4, this);
        this.accountTransactionStatements = t1;
        this.accountTransferStatements = t3;
        this.debitCardStatements = t2;
    }
}
