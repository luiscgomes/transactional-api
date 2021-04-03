package com.transactions.transactionalapi.integrationTests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountReader;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CreateAccountIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountReader accountReader;

    @Autowired
    private AccountWriter accountWriter;

    @Test
    void shouldCreateNewAccount() throws Exception {
        var createAccountModel = new CreateAccountModel("57920370000164");
        final UUID[] accountId = new UUID[1];

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createAccountModel)))
                .andExpect(status().isCreated())
                .andDo(result -> {
                    var body = result.getResponse().getContentAsString();
                    var documentNumber = JsonPath.parse(body).read("document_number").toString();
                    var createdAt = JsonPath.parse(body).read("created_at").toString();
                    accountId[0] = UUID.fromString(JsonPath.parse(body).read("account_id").toString());

                    assertThat(documentNumber).isEqualTo(createAccountModel.getDocumentNumber());
                    assertThat(createdAt).isNotBlank();
                });

        var account = accountReader.one(accountId[0]);
        assertThat(account).isPresent();
    }

    @Test
    void shouldReturnUnprocessableEntityWhenThereIsAlreadyAccountWithDocumentNumber() throws Exception {
        var documentNumber = "07187540000175";
        var account = new Account(documentNumber);

        accountWriter.create(account);

        var createAccountModel = new CreateAccountModel(documentNumber);

        mockMvc.perform(post("/accounts")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(createAccountModel)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(result -> {
                    var body = result.getResponse().getContentAsString();
                    var errorMessage = JsonPath.parse(body).read("$[0]").toString();

                    assertThat(errorMessage).isEqualTo("Document number must be unique. There is already an account with provided document number");
                });
    }
}
