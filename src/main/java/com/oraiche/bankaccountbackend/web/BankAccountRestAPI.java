package com.oraiche.bankaccountbackend.web;

import com.oraiche.bankaccountbackend.dtos.AccountHistoryDto;
import com.oraiche.bankaccountbackend.dtos.BankAccountDto;
import com.oraiche.bankaccountbackend.dtos.OperationDto;
import com.oraiche.bankaccountbackend.exception.BankAcountNotFoundException;
import com.oraiche.bankaccountbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping("/Accounts/{bankAccountId}")
    public BankAccountDto getBankAccount(@PathVariable String bankAccountId) throws BankAcountNotFoundException {
        return bankAccountService.getBankAccount(bankAccountId);
    }
    @GetMapping("/accounts")
    List<BankAccountDto> listAccounts()
    {
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    List<OperationDto> listAccountOperations(@PathVariable(name = "accountId") String Id)
    {
        return bankAccountService.accountHistory(Id);
    }
@GetMapping("/accounts/{accountId}/pageOperations")
    AccountHistoryDto getAccountHistory(@PathVariable String accountId,
                                        @RequestParam(name = "page",defaultValue = "0") int page,
                                        @RequestParam(name = "size",defaultValue = "4") int size) throws BankAcountNotFoundException {
           return  bankAccountService.getAccountHistory(accountId,page,size);
    }
}
