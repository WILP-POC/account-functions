package com.springbank.bank.cmd.api.controllers;

import com.springbank.bank.cmd.api.commands.DepositFundsCommand;
import com.springbank.bank.cmd.api.dto.DepositFundsRequest;
import com.springbank.bank.core.api.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/bank-account/api")
public class DepositFundsController {
    private final CommandGateway commandGateway;

    @Autowired
    public DepositFundsController(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }

    @PutMapping(value = "/v1/deposit/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable String id, @Valid @RequestBody DepositFundsRequest depositFundsRequest){
        var command = DepositFundsCommand.builder()
                .id(id)
                .depositAmount(Double.valueOf(depositFundsRequest.getDepositAmount()))
                .build();
        commandGateway.send(command);
        return new ResponseEntity<>(new BaseResponse("Funds deposit successfully in account "+id), HttpStatus.OK);
    }
}
