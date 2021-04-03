package com.springbank.bank.query.api.handlers;

import com.springbank.bank.core.api.events.AccountClosedEvent;
import com.springbank.bank.core.api.events.AccountOpenedEvent;
import com.springbank.bank.core.api.events.FundsDepositedEvent;
import com.springbank.bank.core.api.events.FundsWithdrawalEvent;

public interface AccountEventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawalEvent event);
    void on(AccountClosedEvent event);
}
