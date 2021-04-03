package com.springbank.bank.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FundsWithdrawalEvent {
    private String id;
    private double amount;
    private double balance;
}
