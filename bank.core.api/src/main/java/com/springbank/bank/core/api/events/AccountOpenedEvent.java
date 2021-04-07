package com.springbank.bank.core.api.events;

import com.springbank.bank.core.api.models.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccountOpenedEvent {

    private String id;
    private String accountHolderId;
    private Date createdDate;
    private AccountType accountType;
    private double accountBalance;
}
