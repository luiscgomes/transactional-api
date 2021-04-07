package com.transactions.transactionalapi.integrationTests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.transactions.transactionalapi.application.models.CreateAccountModel;
import com.transactions.transactionalapi.application.models.CreateCreditLimitModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateCreditLimitIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateCreditLimit() throws Exception {
        var limit = BigDecimal.valueOf(100);
        var creditLimitModel = new CreateCreditLimitModel(limit);

        mockMvc.perform(post("/accounts/{accountId}/credit-limits", UUID.randomUUID())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(creditLimitModel)))
                .andExpect(status().isCreated());
    }
}
