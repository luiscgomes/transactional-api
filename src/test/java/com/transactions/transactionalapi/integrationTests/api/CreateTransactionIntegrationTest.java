package com.transactions.transactionalapi.integrationTests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.transactions.transactionalapi.application.models.CreateTransactionModel;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.enums.OperationTypes;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CreateTransactionIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountWriter accountWriter;

    @Test
    void shouldCreateNewTransaction() throws Exception {
        var account = new Account("57920370000164");
        accountWriter.create(account);

        var createTransactionModel = new CreateTransactionModel(
                account.getId(),
                OperationTypes.Payment.getOperationTypeId(),
                BigDecimal.valueOf(150.30),
                null);

        final UUID[] transactionId = new UUID[1];

        mockMvc.perform(post("/transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createTransactionModel)))
                .andExpect(status().isCreated())
                .andDo(result -> {
                    var body = result.getResponse().getContentAsString();
                    transactionId[0] = UUID.fromString(JsonPath.parse(body).read("transaction_id").toString());
                    var accountId = UUID.fromString(JsonPath.parse(body).read("account_id").toString());
                    var eventDate = JsonPath.parse(body).read("event_date").toString();
                    var operationTypeId = Integer.parseInt(JsonPath.parse(body).read("operation_type_id").toString());
                    var operationTypeDescription = JsonPath.parse(body).read("operation_type_description").toString();
                    var amount = BigDecimal.valueOf(Double.parseDouble(JsonPath.parse(body).read("amount").toString()));

                    assertThat(accountId).isEqualTo(createTransactionModel.getAccountId());
                    assertThat(eventDate).isNotBlank();
                    assertThat(operationTypeId).isEqualTo(createTransactionModel.getOperationTypeId());
                    assertThat(operationTypeDescription).isEqualTo(OperationTypes.Payment.name());
                    assertThat(amount).isEqualTo(createTransactionModel.getAmount());
                });
    }

    @Test
    void shouldReturnUnprocessableEntityWhenAccountDoesNotExist() throws Exception {
        var createTransactionModel = new CreateTransactionModel(
                UUID.randomUUID(),
                OperationTypes.CashPurchase.getOperationTypeId(),
                BigDecimal.valueOf(300),
                null);

        mockMvc.perform(post("/transactions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createTransactionModel)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(result -> {
                    var body = result.getResponse().getContentAsString();
                    var errorMessage = JsonPath.parse(body).read("$[0]").toString();

                    assertThat(errorMessage).isEqualTo("Account does not exit for provided account id");
                });
    }
}
