package com.oraiche.bankaccountbackend.entities;

import com.oraiche.bankaccountbackend.enums.OperType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Operation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date opetDate;
    private double amount;
    private String description;
    private OperType operType;
    @ManyToOne
    private BankAccount bankAccount;

}
