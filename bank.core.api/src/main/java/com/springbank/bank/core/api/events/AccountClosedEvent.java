package com.springbank.bank.core.api.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountClosedEvent {
    private String id;
}
