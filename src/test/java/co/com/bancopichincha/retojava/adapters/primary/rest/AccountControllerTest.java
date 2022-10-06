package co.com.bancopichincha.retojava.adapters.primary.rest;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.AccountRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;
import co.com.bancopichincha.retojava.domain.Account;
import co.com.bancopichincha.retojava.domain.Client;
import co.com.bancopichincha.retojava.domain.enums.AccountType;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest {

    private final String baseUrl = "http://localhost:8080/cuentas";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    private static ObjectMapper mapper;

    private static AccountResponse accountResponse;

    private static AccountRequest accountRequest;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        long accountNumber = 435443L;
        double initialBalance = 200;
        boolean status = true;
        accountRequest = new AccountRequest();
        accountRequest.setNumber(accountNumber);
        accountRequest.setType(AccountType.AHORROS.name());
        accountRequest.setInitialBalance(initialBalance);
        accountRequest.setStatus(status);
        accountRequest.setClientId(1L);
        accountResponse = new AccountResponse();
        accountResponse.setClientId(1L);
        accountResponse.setNumber(accountNumber);
        accountResponse.setStatus(status);
        accountResponse.setType(AccountType.AHORROS.name());
        accountResponse.setInitialBalance(initialBalance);
    }

    @Test
    @Order(1)
    void givenValidAccountInfoWhenSaveAccountThenReturnCreatedStatus() throws Exception {
        when(service.saveAccount(any())).thenReturn(accountResponse);
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(accountRequest))).andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(accountResponse)));
    }

    @Test
    @Order(2)
    void givenNotValidAccountInfoWhenSaveAccountThenReturnBadRequestStatus() throws Exception {
        accountRequest.setNumber(null);
        this.mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    void givenFilterParamsWhenGetAccountsInfoThenReturnOkStatus() throws Exception {
        when(service.getAccounts(null, AccountType.AHORROS.name(), null, null))
                .thenReturn(List.of(accountResponse));
        this.mockMvc.perform(get(baseUrl).contentType(MediaType.APPLICATION_JSON)
                .param("type", AccountType.AHORROS.name())).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(List.of(accountResponse))));
    }

    @Test
    @Order(4)
    void givenValidAccountIdentifierWhenGetAccountThenReturnOkStatus() throws Exception {
        when(service.getAccount(accountResponse.getId())).thenReturn(accountResponse);
        this.mockMvc.perform(get(baseUrl + "/{id}", accountResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(accountResponse)));
    }

    @Test
    @Order(5)
    void givenNotValidAccountIdentifierWhenGetAccountThenReturnNotFoundStatus() throws Exception {
        when(service.getAccount(accountResponse.getId())).thenThrow(NotFoundException.class);
        this.mockMvc.perform(get(baseUrl + "/{id}", accountResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void givenValidAccountIdentifierWhenDeleteAccountThenReturnNoContentStatus() throws Exception {
        this.mockMvc.perform(delete(baseUrl + "/{id}", accountResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    @Order(7)
    void givenNotValidAccountIdentifierWhenDeleteAccountThenReturnNotFoundStatus() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteAccount(accountResponse.getId());
        this.mockMvc.perform(delete(baseUrl + "/{id}", accountResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

}