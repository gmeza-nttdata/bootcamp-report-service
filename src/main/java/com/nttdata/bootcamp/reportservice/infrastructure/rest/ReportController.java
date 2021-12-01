package com.nttdata.bootcamp.reportservice.infrastructure.rest;

import com.nttdata.bootcamp.reportservice.application.ReportOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportOperations operations;

}
