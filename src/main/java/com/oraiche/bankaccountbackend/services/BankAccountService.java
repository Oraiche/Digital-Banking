package com.oraiche.bankaccountbackend.services;

import com.mysql.cj.xdevapi.DbDoc;
import com.oraiche.bankaccountbackend.dtos.*;
import com.oraiche.bankaccountbackend.entities.*;
import com.oraiche.bankaccountbackend.exception.BalanceNotSufficientException;
import com.oraiche.bankaccountbackend.exception.BankAcountNotFoundException;
import com.oraiche.bankaccountbackend.exception.customerNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    List<BankAccountDto> bankAccountList();
    List<CustomerDto> listCustomer();

    CustomerDto getCustomer(Long customerId) throws customerNotFoundException;

    BankAccountDto getBankAccount(String id) throws BankAcountNotFoundException;

    void debit(String bankAccountId,double amount, String description) throws BankAcountNotFoundException, BalanceNotSufficientException;

    void credit(String bankAccountId, double amount, String description) throws BankAcountNotFoundException;


    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long customerId);
    CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long CustomerId) throws customerNotFoundException;
    SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long CustomerId) throws customerNotFoundException;
    void transfer(String AccountIdSource, String AccountIdDestination, double amount) throws BalanceNotSufficientException, BankAcountNotFoundException;

    List<OperationDto> accountHistory(String accountId);
    AccountHistoryDto getAccountHistory(String accountId,int page,int size) throws BankAcountNotFoundException;
}
