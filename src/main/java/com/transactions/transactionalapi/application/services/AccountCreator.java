package com.transactions.transactionalapi.application.services;

import com.transactions.transactionalapi.application.models.AccountCreatedModel;
import com.transactions.transactionalapi.application.models.CreateAccountModel;

import java.util.Optional;

public interface AccountCreator {
    Optional<AccountCreatedModel> create(CreateAccountModel account);
}
