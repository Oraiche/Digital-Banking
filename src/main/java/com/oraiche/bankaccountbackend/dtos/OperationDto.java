package com.oraiche.bankaccountbackend.dtos;

import com.oraiche.bankaccountbackend.enums.OperType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
public class OperationDto {
    private Long id;
    private Date opetDate;
    private double amount;
    private String description;
    private OperType operType;
}
