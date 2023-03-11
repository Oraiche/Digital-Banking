package com.oraiche.bankaccountbackend.services;

import com.oraiche.bankaccountbackend.dtos.*;
import com.oraiche.bankaccountbackend.entities.*;
import com.oraiche.bankaccountbackend.enums.AccountStatus;
import com.oraiche.bankaccountbackend.enums.OperType;
import com.oraiche.bankaccountbackend.exception.BalanceNotSufficientException;
import com.oraiche.bankaccountbackend.exception.BankAcountNotFoundException;
import com.oraiche.bankaccountbackend.exception.customerNotFoundException;
import com.oraiche.bankaccountbackend.mappers.BankAccountMapper;
import com.oraiche.bankaccountbackend.repositories.BankAccountRepository;
import com.oraiche.bankaccountbackend.repositories.CustomerRepository;
import com.oraiche.bankaccountbackend.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountImp implements BankAccountService{
    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private OperationRepository operationRepository;
    private BankAccountMapper dtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("saving new Customer"); //Journalisation

        Customer customer = dtoMapper.fromCustomerDto(customerDto);

        Customer savedCustomer = customerRepository.save(customer);

        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<BankAccountDto> bankAccountList() {
        List <BankAccount> bankAccountList= bankAccountRepository.findAll();
       List<BankAccountDto> bankAccountDtoList = bankAccountList.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());

       return bankAccountDtoList;
    }

    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws customerNotFoundException {
       Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new customerNotFoundException("customer Not Found");

          CurrentAccount currentAccount = new CurrentAccount();
          currentAccount.setId(UUID.randomUUID().toString());
          currentAccount.setCreatedAt(new Date());
          currentAccount.setOverDraft(overDraft);
          currentAccount.setBalance(initialBalance);
          currentAccount.setCustomer(customer);
          currentAccount.setAccStatus(AccountStatus.ACTIVATED);

          CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);
          return dtoMapper.fromCurrentAccount(savedCurrentAccount);
    }
     @Override
    public CustomerDto updateCustomer(CustomerDto customerDto)
    {
        log.info("update Customer"); //Journalisation

        Customer customer = dtoMapper.fromCustomerDto(customerDto);

        Customer savedCustomer = customerRepository.save(customer);

        return dtoMapper.fromCustomer(savedCustomer);
    }
     @Override
    public void  deleteCustomer(Long customerId)
    {
     customerRepository.deleteById(customerId);
    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws customerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null) throw new customerNotFoundException("customer Not Found");

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setAccStatus(AccountStatus.ACTIVATED);

        SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingAccount(savedSavingAccount);
    }
    @Override
    public List<CustomerDto> listCustomer() {
        List<Customer> customers=customerRepository.findAll();

       List<CustomerDto> customerDtos=customers.stream().
                map(customer -> dtoMapper.fromCustomer(customer)).        // Fonctionnall programming
                collect(Collectors.toList());

      /*  List<CustomerDto> customerDtos=new ArrayList<>();
        for (Customer customer:customers)
        {
            CustomerDto customerDto=new CustomerDto();
            customerDto=dtoMapper.fromCustomer(customer);                 // imperative programming
            customerDtos.add(customerDto);
        }*/

        return customerDtos;
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> listCustomers=customerRepository.searchCustomerByNameContains(keyword);
        List<CustomerDto> customersDtos = listCustomers.stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customersDtos;
    }

    @Override
          public CustomerDto getCustomer(Long customerId) throws customerNotFoundException
           {
                 Customer customer = customerRepository.findById(customerId).
                 orElseThrow(()->new customerNotFoundException("Customer not found"));
                 CustomerDto customerDto = dtoMapper.fromCustomer(customer);
               return customerDto;
           }


    @Override
    public BankAccountDto getBankAccount(String id) throws BankAcountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(id).orElse(null);
        if (bankAccount==null) throw new BankAcountNotFoundException("Bank Account Not Found");

        if (bankAccount instanceof SavingAccount) {
           SavingBankAccountDto savingBankAccountDto=dtoMapper.fromSavingAccount ((SavingAccount) bankAccount);
           return savingBankAccountDto;
        }
        else
        {CurrentBankAccountDto currentBankAccountDto=dtoMapper.fromCurrentAccount((CurrentAccount) bankAccount);
            return currentBankAccountDto;
        }

    }

    @Override
    public void debit(String bankAccountId, double amount, String description) throws BankAcountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(bankAccountId).orElse(null);
        if (bankAccount==null) throw new BankAcountNotFoundException("Bank Account Not Found");

        {
            if(bankAccount.getBalance()<amount) throw new BalanceNotSufficientException("balance not sufficient");
            Operation operation=new Operation();
            operation.setOpetDate(new Date());
            operation.setAmount(amount);
            operation.setBankAccount(bankAccount);
            operation.setOperType(OperType.DEBIT);
            operation.setDescription("Debit");
            operationRepository.save(operation);

            bankAccount.setBalance(bankAccount.getBalance()-amount);

            bankAccountRepository.save(bankAccount);
        }



    }

    @Override
    public void credit(String bankAccountId, double amount, String description) throws BankAcountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(bankAccountId).orElse(null);
        if (bankAccount==null) throw new BankAcountNotFoundException("Bank Account Not Found");
        Operation operation=new Operation();
        operation.setOpetDate(new Date());
        operation.setAmount(amount);
        operation.setBankAccount(bankAccount);
        operation.setOperType(OperType.CREDIT);
        operation.setDescription("there is a Credity");
        operationRepository.save(operation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);

        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BalanceNotSufficientException, BankAcountNotFoundException {

        debit(accountIdSource,amount,"Transfer to"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);
    }
  @Override
    public List<OperationDto> accountHistory(String accountId)
    {
        List<Operation> accountsOperations=operationRepository.findByBankAccountId(accountId);
        List<OperationDto> operationDtoList = accountsOperations.stream().map(operation -> {
            return dtoMapper.fromOperation(operation);
        }).collect(Collectors.toList());

        return operationDtoList;
    }

    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAcountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);

        if (bankAccount==null) throw new BankAcountNotFoundException("Account Not Found");

        AccountHistoryDto accountHistoryDto=new AccountHistoryDto();
        Page<Operation> operationPage = operationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));

        List<OperationDto> operationDtos = operationPage.getContent().stream().map(
                operation -> dtoMapper.fromOperation(operation)).collect(Collectors.toList());

        accountHistoryDto.setOperationDtoList(operationDtos);
        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(operationPage.getTotalPages());
        return accountHistoryDto;
    }
}
