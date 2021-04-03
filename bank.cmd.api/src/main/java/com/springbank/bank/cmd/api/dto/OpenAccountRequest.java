package com.springbank.bank.cmd.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.springbank.bank.core.api.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"accountHolderId","accountType","openingBalance"})
public class OpenAccountRequest {

    @JsonProperty("accountHolderId")
    @NotBlank(message = "account holder id is required")
    private String accountHolderId;

    @JsonProperty("accountType")
    @NotBlank(message = "account type needed")
    private AccountType accountType;

    @JsonProperty("openingBalance")
    @Min(value = 5000,message = "Minimum 5000")
    private double openingBalance;

}
