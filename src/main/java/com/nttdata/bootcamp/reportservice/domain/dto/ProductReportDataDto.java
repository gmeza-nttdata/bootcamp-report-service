package com.nttdata.bootcamp.reportservice.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductReportDataDto {
    private String id;
    private LocalDate from;
    private LocalDate to;
}
