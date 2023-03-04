package com.oraiche.bankaccountbackend.mappers;

import com.oraiche.bankaccountbackend.dtos.CurrentBankAccountDto;
import com.oraiche.bankaccountbackend.dtos.CustomerDto;
import com.oraiche.bankaccountbackend.dtos.OperationDto;
import com.oraiche.bankaccountbackend.dtos.SavingBankAccountDto;
import com.oraiche.bankaccountbackend.entities.CurrentAccount;
import com.oraiche.bankaccountbackend.entities.Customer;
import com.oraiche.bankaccountbackend.entities.Operation;
import com.oraiche.bankaccountbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public CustomerDto fromCustomer(Customer customer)
    {
        CustomerDto customerDto=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);  // peut etre remplacer par le framwork MapStruct
        return customerDto;
    }

    public Customer fromCustomerDto(CustomerDto customerDto)
    {
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount)
    {
        SavingBankAccountDto savingBankAccountDto=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDto);

        savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDto;
    }

    public SavingAccount fromSavingAccountDto(SavingBankAccountDto savingBankAccountDto)
    {
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto, savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccountDto.getCustomerDto()));
        return savingAccount;
    }

    public CurrentBankAccountDto fromCurrentAccount(CurrentAccount currentAccount)
    {
        CurrentBankAccountDto currentBankAccountDto=new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDto);

       currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
       currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDto;
    }

    public CurrentAccount fromCurrentAccountDto(CurrentBankAccountDto currentBankAccountDto)
    {
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public OperationDto fromOperation(Operation operation)
    {
        OperationDto operationDto=new OperationDto();
        BeanUtils.copyProperties(operation, operationDto);

        return operationDto;
    }

    public Operation fromOperationDto(OperationDto operationDto)
    {
        Operation operation=new Operation();
        BeanUtils.copyProperties(operation, operationDto);

        return operation;
    }




}
