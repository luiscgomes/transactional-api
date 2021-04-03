package com.transactions.transactionalapi.application.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandResult<T> {
    @Getter
    private Optional<T> value;

    @Getter
    private List<String> errors;

    public CommandResult() {
        errors = new ArrayList<>();
    }

    public CommandResult(T value) {
        if (value == null)
            throw new IllegalArgumentException("value can not be null");

        this.value = Optional.of(value);
    }

    public boolean hasError() {
        return errors != null && errors.isEmpty() == false;
    }

    public void addError(String errorMessage) {
        errors.add(errorMessage);
    }
}
