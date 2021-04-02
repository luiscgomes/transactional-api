package com.transactions.transactionalapi.application.services;

import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.models.CreateAccountModel;

import java.util.Optional;

public interface AccountCreator {
    Optional<CreatedAccountModel> create(CreateAccountModel account);
}
