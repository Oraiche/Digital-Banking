package com.oraiche.bankaccountbackend.repositories;

import com.oraiche.bankaccountbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    public List<Customer> searchCustomerByNameContains(String keyword);
}
