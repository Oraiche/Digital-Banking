package com.oraiche.bankaccountbackend.repositories;

import com.oraiche.bankaccountbackend.entities.Operation;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {
    public List<Operation> findByBankAccountId(String accountId);
    public Page<Operation> findByBankAccountId(String accountId,Pageable pageable);
}
