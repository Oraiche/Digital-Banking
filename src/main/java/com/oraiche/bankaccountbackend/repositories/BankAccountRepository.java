package com.oraiche.bankaccountbackend.repositories;

import com.oraiche.bankaccountbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
