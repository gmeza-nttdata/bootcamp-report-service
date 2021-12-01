package com.nttdata.bootcamp.reportservice.application.impl;

import com.nttdata.bootcamp.reportservice.application.service.AccountTransactionService;
import com.nttdata.bootcamp.reportservice.application.service.AccountTransferService;
import com.nttdata.bootcamp.reportservice.application.service.CreditTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportOperationsImpl {

    private final AccountTransactionService accountTransactionService;
    private final AccountTransferService accountTransferService;
    private final CreditTransactionService creditTransactionService;



}
