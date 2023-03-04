package com.oraiche.bankaccountbackend;

import com.oraiche.bankaccountbackend.dtos.BankAccountDto;
import com.oraiche.bankaccountbackend.dtos.CurrentBankAccountDto;
import com.oraiche.bankaccountbackend.dtos.CustomerDto;
import com.oraiche.bankaccountbackend.dtos.SavingBankAccountDto;
import com.oraiche.bankaccountbackend.entities.*;
import com.oraiche.bankaccountbackend.enums.AccountStatus;
import com.oraiche.bankaccountbackend.enums.OperType;
import com.oraiche.bankaccountbackend.exception.BalanceNotSufficientException;
import com.oraiche.bankaccountbackend.exception.BankAcountNotFoundException;
import com.oraiche.bankaccountbackend.exception.customerNotFoundException;
import com.oraiche.bankaccountbackend.repositories.BankAccountRepository;
import com.oraiche.bankaccountbackend.repositories.CustomerRepository;
import com.oraiche.bankaccountbackend.repositories.OperationRepository;
import com.oraiche.bankaccountbackend.services.BankAccountService;
import jakarta.persistence.DiscriminatorValue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankAccountBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAccountBackEndApplication.class, args);
    }

     @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {

            Stream.of("Yasser","Najwa","Khadija").forEach(name->{
                        CustomerDto customerDto = new CustomerDto();
                        customerDto.setName(name);
                        customerDto.setEmail(name+"@gmail.com");

                        bankAccountService.saveCustomer(customerDto);
                    });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*8000,2000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*9000,5,customer.getId());

                } catch (customerNotFoundException e) {
                    e.getStackTrace();}
            });

            List<BankAccountDto> bankAccountList= bankAccountService.bankAccountList();
            String accountId;
            for (BankAccountDto bankAccount:bankAccountList)
                for (int i=0;i<10;i++)
                {
                    if(bankAccount instanceof SavingBankAccountDto){
                        accountId=((SavingBankAccountDto) bankAccount).getId();
                    }
                    else{
                        accountId=((CurrentBankAccountDto) bankAccount).getId();
                    }

                    bankAccountService.credit(accountId, 1000 + Math.random() * 3000, "Credit");
                    bankAccountService.debit(accountId, Math.random() * 500, "debit");
                }
        };
    }

























    //@Bean
    CommandLineRunner star(BankAccountRepository bankAccountRepository,
                           OperationRepository operationRepository,
            CustomerRepository customerRepository)
    {
        return args -> {

            Stream.of("Mohammed","hamid","Sara").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
                    });

                  customerRepository.findAll().forEach(customer ->{
                 CurrentAccount currentAccount=new CurrentAccount();
                  currentAccount.setId(UUID.randomUUID().toString());
                  currentAccount.setAccStatus(AccountStatus.ACTIVATED);
                  currentAccount.setBalance(Math.random()*80000);
                  currentAccount.setCreatedAt(new Date());
                  currentAccount.setCustomer(customer);
                  currentAccount.setCurrency(Math.random()>0.5?"Dh":"Dollar");
                  currentAccount.setOverDraft(80000);


                  bankAccountRepository.save(currentAccount);

                  SavingAccount savingAccount=new SavingAccount();
                  savingAccount.setId(UUID.randomUUID().toString());
                  savingAccount.setAccStatus(AccountStatus.ACTIVATED);
                  savingAccount.setCustomer(customer);
                  savingAccount.setCurrency(Math.random()>0.5?"Dh":"Dollar");
                  savingAccount.setBalance(9000);
                  savingAccount.setCreatedAt(new Date());
                  savingAccount.setInterestRate(5.5);
                  bankAccountRepository.save(savingAccount);

                      });


                  bankAccountRepository.findAll().forEach(bankAccount -> {
                      for (int i=0;i<10;i++)
                      {
                          Operation operation=new Operation();
                          operation.setAmount(Math.random()*50000);
                          operation.setOperType(Math.random()>0.5? OperType.CREDIT:OperType.DEBIT);
                          operation.setBankAccount(bankAccount);
                          operationRepository.save(operation);
                      }
                  });




        };
    }

}
