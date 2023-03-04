package com.oraiche.bankaccountbackend.entities;


import com.oraiche.bankaccountbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type",length = 4)
@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountStatus accStatus;
      @ManyToOne
    private Customer customer;
      @OneToMany(mappedBy = "bankAccount",cascade =CascadeType.ALL)
    private List<Operation> operations;


}
