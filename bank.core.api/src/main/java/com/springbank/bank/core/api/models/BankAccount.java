package com.springbank.bank.core.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "account_holder_id")
    private String accountHolderId;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "type")
    private AccountType type;
    @Column(name = "balance")
    private double balance;

}
