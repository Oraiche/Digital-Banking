package com.oraiche.bankaccountbackend.dtos;

import com.oraiche.bankaccountbackend.enums.AccountStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data   @AllArgsConstructor @NoArgsConstructor
public class SavingBankAccountDto extends BankAccountDto{
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus accStatus;
    private CustomerDto customerDto;
    private double interestRate;
}
