package com.springbank.bank.query.api.handlers;

import com.springbank.bank.core.api.events.AccountClosedEvent;
import com.springbank.bank.core.api.events.AccountOpenedEvent;
import com.springbank.bank.core.api.events.FundsDepositedEvent;
import com.springbank.bank.core.api.events.FundsWithdrawalEvent;
import com.springbank.bank.core.api.models.BankAccount;
import com.springbank.bank.query.api.repositories.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler{
    private AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }
    @Override
    @EventHandler
    public void on(AccountOpenedEvent event) {
      var bankAccount = BankAccount.builder()
              .id(event.getId())
              .accountHolderId(event.getAccountHolderId())
              .creationDate(event.getCreatedDate())
              .balance(event.getAccountBalance())
              .type(event.getAccountType()).build();
      accountRepository.save(bankAccount);
    }

    @Override
    @EventHandler
    public void on(FundsDepositedEvent event) {
       var bankAccount = accountRepository.findById(event.getId());
       if(bankAccount.isEmpty())
           return;
       bankAccount.get().setBalance(event.getBalance());
       accountRepository.save(bankAccount.get());
    }

    @Override
    @EventHandler
    public void on(FundsWithdrawalEvent event) {
       var bankAccount = accountRepository.findById(event.getId());
       if (bankAccount.isEmpty()) return;
       bankAccount.get().setBalance(event.getBalance());
       accountRepository.save(bankAccount.get());
    }

    @Override
    @EventHandler
    public void on(AccountClosedEvent event) {
       var bankAccount = accountRepository.findById(event.getId());
       if (bankAccount.isEmpty()) return;
       accountRepository.delete(bankAccount.get());
    }
}
