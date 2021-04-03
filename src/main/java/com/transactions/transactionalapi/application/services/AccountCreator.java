package com.transactions.transactionalapi.application.services;

import com.transactions.transactionalapi.application.models.CommandResult;
import com.transactions.transactionalapi.application.models.CreatedAccountModel;
import com.transactions.transactionalapi.application.models.CreateAccountModel;

public interface AccountCreator {
    CommandResult<CreatedAccountModel> create(CreateAccountModel account);
}
