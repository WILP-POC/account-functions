package com.springbank.bank.query.api.repositories;

import com.springbank.bank.core.api.models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<BankAccount, String> {
}
