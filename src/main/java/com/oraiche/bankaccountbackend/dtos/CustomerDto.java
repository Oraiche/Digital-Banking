package com.oraiche.bankaccountbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oraiche.bankaccountbackend.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;

}
