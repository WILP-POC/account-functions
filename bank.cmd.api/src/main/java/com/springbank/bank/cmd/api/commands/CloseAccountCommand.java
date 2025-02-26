package com.springbank.bank.cmd.api.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CloseAccountCommand {

    @TargetAggregateIdentifier
    private String id;
}
