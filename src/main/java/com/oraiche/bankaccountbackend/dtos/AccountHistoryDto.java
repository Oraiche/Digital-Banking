package com.oraiche.bankaccountbackend.dtos;

import com.oraiche.bankaccountbackend.entities.Operation;
import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDto {
    private String accountId;
    private  int currentPage;
    private int totalPages;
    private int pageSize;
    private double balance;
    private List<OperationDto> operationDtoList;
}
