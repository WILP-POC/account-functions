package com.springbank.bank.cmd.api.controllers;

import com.springbank.bank.cmd.api.commands.OpenAccountCommand;
import com.springbank.bank.cmd.api.dto.OpenAccountRequest;
import com.springbank.bank.cmd.api.dto.OpenAccountResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/bank-account/api")
public class OpenAccountController {
    private final CommandGateway commandGateway;

    @Autowired
    public OpenAccountController(CommandGateway commandGateway){
        this.commandGateway=commandGateway;
    }

    @PostMapping(value = "/v1/account")
    public ResponseEntity<OpenAccountResponse> openAccount(@Valid @RequestBody OpenAccountRequest openAccountRequest) throws ExecutionException, InterruptedException {
        var id = UUID.randomUUID().toString();
        OpenAccountCommand openAccountCommand = OpenAccountCommand.builder()
                .id(id)
                .accountHolderId(openAccountRequest.getAccountHolderId())
                .accountType(openAccountRequest.getAccountType())
                .openingBalance(openAccountRequest.getOpeningBalance())
                .build();
        try {
            commandGateway.send(openAccountCommand);
            return new ResponseEntity<>(new OpenAccountResponse(id, "successfully opened a new bank account!"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeErrorMessage = "Error while processing request to open a new bank account for id - " + id;
            System.out.println(e.toString());

            return new ResponseEntity<>(new OpenAccountResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
