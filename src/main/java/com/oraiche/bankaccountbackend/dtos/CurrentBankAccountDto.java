package com.oraiche.bankaccountbackend.dtos;

import com.oraiche.bankaccountbackend.entities.Customer;
import com.oraiche.bankaccountbackend.entities.Operation;
import com.oraiche.bankaccountbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentBankAccountDto extends BankAccountDto {
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus accStatus;
    private CustomerDto customerDto;
    private double overDraft;

}
