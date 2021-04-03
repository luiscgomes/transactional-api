package com.transactions.transactionalapi.integrationTests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.transactions.transactionalapi.domain.entities.Account;
import com.transactions.transactionalapi.domain.repositories.AccountWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAccountIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountWriter accountWriter;

    @Test
    void shouldReturnAccountWhenItExists() throws Exception {
        var account = new Account("57920370000164");

        accountWriter.create(account);

        mockMvc.perform(get("/accounts/{accountId}", account.getId().toString()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    var body = result.getResponse().getContentAsString();
                    var accountId = UUID.fromString(JsonPath.parse(body).read("account_id").toString());
                    var createdAt = LocalDateTime.parse(JsonPath.parse(body).read("created_at").toString());
                    var documentNumber = JsonPath.parse(body).read("document_number").toString();

                    assertThat(accountId).isEqualTo(account.getId());
                    assertThat(createdAt).isEqualTo(account.getCreatedAt());
                    assertThat(documentNumber).isEqualTo(account.getDocumentNumber().getNumber());
                });
    }

    @Test
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        mockMvc.perform(get("/accounts/{accountId}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }
}
