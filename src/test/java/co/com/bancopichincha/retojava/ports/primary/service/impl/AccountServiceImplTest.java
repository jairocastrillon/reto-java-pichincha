package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.AccountRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;
import co.com.bancopichincha.retojava.domain.Account;
import co.com.bancopichincha.retojava.domain.Client;
import co.com.bancopichincha.retojava.domain.enums.AccountType;
import co.com.bancopichincha.retojava.domain.repository.AccountRepository;
import co.com.bancopichincha.retojava.exception.BadRequestException;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository repository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private AccountServiceImpl accountService;

    private static AccountRequest accountRequest;

    private static AccountResponse accountResponse;

    private static Account account;

    private static ClientResponse clientResponse;

    @BeforeAll
    public static void setUp() {
        long accountNumber = 435443L;
        double initialBalance = 200;
        boolean status = true;
        account = new Account(1L, accountNumber, AccountType.AHORROS, initialBalance,
                status, new Client(), new ArrayList<>());
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
        clientResponse = new ClientResponse();
        clientResponse.setId(1L);
        clientResponse.setStatus(true);
    }

    @Test
    @Order(1)
    void givenValidAccountInfoWhenSaveAccountThenReturnAccountPersistedInfo() {
        when(clientService.getClient(accountRequest.getClientId())).thenReturn(clientResponse);
        when(mapper.map(any(), eq(Account.class))).thenReturn(account);
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        when(repository.save(account)).thenReturn(account);
        AccountResponse accountSaved = accountService.saveAccount(accountRequest);
        assertNotNull(accountSaved);
    }

    @Test
    @Order(2)
    void givenNotValidClientAssociatedToAccountWhenSaveAccountThenThrowsNotFoundException() {
        when(clientService.getClient(accountRequest.getClientId())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> accountService.saveAccount(accountRequest));
    }

    @Test
    @Order(3)
    void givenNotValidTypeAccountWhenSaveAccountThenThrowsNotFoundException() {
        accountRequest.setType("NOT_TYPE");
        when(clientService.getClient(accountRequest.getClientId())).thenReturn(clientResponse);
        assertThrows(NotFoundException.class, () -> accountService.saveAccount(accountRequest));
    }

    @Test
    @Order(4)
    void givenNotParamsFilterWhenGetAccountsThenReturnAccountsInfo() {
        when(repository.getAccounts(null, null, null, null))
                .thenReturn(List.of(account));
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        List<AccountResponse> accounts = accountService.getAccounts(null, null, null, null);
        assertAll(() -> assertNotNull(accounts), () -> assertEquals(1, accounts.size()));
    }

    @Test
    @Order(5)
    void givenNotValidTypeAccountWhenGetAccountsThenThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> accountService.getAccounts(null,
                "ok", null, null));
    }

    @Test
    @Order(6)
    void givenValidAccountIdentifierWhenGetAccountThenReturnAccountInfo() {
        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        AccountResponse accountResp = accountService.getAccount(account.getId());
        assertNotNull(accountResp);
    }

    @Test
    @Order(7)
    void givenNotValidAccountIdentifierWhenGetAccountThenThrowsNotFoundException() {
        long id = account.getId();
        when(repository.findById(account.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.getAccount(id));
    }

    @Test
    @Order(8)
    void givenValidAccountNumberWhenGetAccountByNumberThenReturnAccountInfo() {
        when(repository.findByNumber(account.getNumber())).thenReturn(Optional.of(account));
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        AccountResponse accountResp = accountService.getAccountByNumber(account.getNumber());
        assertNotNull(accountResp);
    }

    @Test
    @Order(9)
    void givenNotValidAccountNumberWhenGetAccountByNumberThenThrowsNotFoundException() {
        long number = account.getNumber();
        when(repository.findByNumber(account.getNumber())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.getAccountByNumber(number));
    }

    @Test
    @Order(10)
    void givenValidAccountIdentifierWhenDeleteAccountThenDoesntThrow() {
        when(repository.findById(account.getId())).thenReturn(Optional.of(account));
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        assertDoesNotThrow(() -> accountService.deleteAccount(account.getId()));
    }

    @Test
    @Order(11)
    void givenNotValidAccountIdentifierWhenDeleteAccountThenThrowsNotFoundException() {
        long id = account.getId();
        when(repository.findById(account.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.deleteAccount(id));
    }

    @Test
    @Order(12)
    void givenValidAccountInfoButClientDisabledWhenSaveAccountThenReturnBadRequestException() {
        clientResponse.setStatus(false);
        when(clientService.getClient(accountRequest.getClientId())).thenReturn(clientResponse);
        assertThrows(BadRequestException.class, () -> accountService.saveAccount(accountRequest));
    }

    @Test
    @Order(13)
    void givenValidAccountInfoWhenUpdateAccountThenDoesntThrow() {
        accountRequest.setType(AccountType.AHORROS.name());
        when(repository.findById(anyLong())).thenReturn(Optional.of(account));
        when(mapper.map(any(), eq(AccountResponse.class))).thenReturn(accountResponse);
        when(mapper.map(any(), eq(Account.class))).thenReturn(account);
        when(repository.save(any())).thenReturn(account);
        assertDoesNotThrow(() -> accountService.updateAccount(account.getId(), accountRequest));
    }

    @Test
    @Order(13)
    void givenNotValidValidAccountInfoWhenUpdateAccountThenThrowsNotFoundException() {
        accountRequest.setType("ok");
        assertThrows(NotFoundException.class, () -> accountService.updateAccount(account.getId(),
                accountRequest));
    }

}