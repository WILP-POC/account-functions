package com.springbank.bank.cmd.api.aggregates;

import com.springbank.bank.cmd.api.commands.CloseAccountCommand;
import com.springbank.bank.cmd.api.commands.DepositFundsCommand;
import com.springbank.bank.cmd.api.commands.OpenAccountCommand;
import com.springbank.bank.cmd.api.commands.WithdrawAmountCommand;
import com.springbank.bank.core.api.events.AccountClosedEvent;
import com.springbank.bank.core.api.events.AccountOpenedEvent;
import com.springbank.bank.core.api.events.FundsDepositedEvent;
import com.springbank.bank.core.api.events.FundsWithdrawalEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private double balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command) {
        var event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .accountBalance(command.getOpeningBalance())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event) {
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getAccountBalance();
    }

    @CommandHandler
    public void handle(DepositFundsCommand command) {
        var amount = command.getDepositAmount();
        var event = FundsDepositedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance + amount)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent event) {
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(WithdrawAmountCommand command) {
        var amount = command.getWithdrawalAmount();

        if (this.balance - amount < 0) {
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }

        var event = FundsWithdrawalEvent.builder()
                .id(command.getId())
                .amount(command.getWithdrawalAmount())
                .balance(this.balance - amount)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsWithdrawalEvent event) {
        this.balance -= event.getAmount();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command) {
        var event = AccountClosedEvent.builder()
                .id(command.getId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
