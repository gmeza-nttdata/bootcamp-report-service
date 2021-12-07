package com.nttdata.bootcamp.reportservice.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateDto {
    private LocalDate from;
    private LocalDate to;
}
