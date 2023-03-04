package com.oraiche.bankaccountbackend.web;

import com.oraiche.bankaccountbackend.dtos.CustomerDto;
import com.oraiche.bankaccountbackend.entities.Customer;
import com.oraiche.bankaccountbackend.exception.customerNotFoundException;
import com.oraiche.bankaccountbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor


public class CustomerRestController {
    private BankAccountService bankAccountService;
    @RequestMapping("/customers")
    public List<CustomerDto> customer(){

         return bankAccountService.listCustomer();
    }
    @GetMapping("/customers/{id}")
    public  CustomerDto getCustomer(@PathVariable (name = "id") Long customerId) throws customerNotFoundException {
           return bankAccountService.getCustomer(customerId);
    }
     @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto)
    {
       return bankAccountService.saveCustomer(customerDto);
    }
     @PutMapping("customers/{customerId}")
    public CustomerDto updateCustomer( @PathVariable Long customerId,@RequestBody  CustomerDto customerDto)
    {
           customerDto.setId(customerId);
           return bankAccountService.updateCustomer(customerDto);
    }
    @DeleteMapping("customers/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long id)
    {
       bankAccountService.deleteCustomer(id);
    }
}
