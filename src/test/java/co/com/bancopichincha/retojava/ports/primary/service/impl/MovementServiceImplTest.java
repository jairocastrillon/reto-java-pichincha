package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.MovementRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.MovementResponse;
import co.com.bancopichincha.retojava.domain.Account;
import co.com.bancopichincha.retojava.domain.Client;
import co.com.bancopichincha.retojava.domain.Movement;
import co.com.bancopichincha.retojava.domain.repository.AccountRepository;
import co.com.bancopichincha.retojava.domain.repository.MovementRepository;
import co.com.bancopichincha.retojava.exception.BadRequestException;
import co.com.bancopichincha.retojava.ports.primary.service.AccountService;
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

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovementServiceImplTest {

    @Mock
    private MovementRepository repository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private MovementServiceImpl movementService;

    private static MovementResponse movementResponse;

    private static MovementRequest movementRequest;

    private static Movement movement;

    private static Account accountResponse;

    @BeforeAll
    public static void setUp() {
        long id = 1L;
        double amount = 200;
        double balance = 300;
        movement = new Movement(id, new Account(), new Date(), amount, balance);
        movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(1L);
        movementRequest.setAmount(amount);
        movementResponse = new MovementResponse();
        movementResponse.setAccountNumber(1L);
        movementResponse.setAmount(amount);
        movementResponse.setBalance(balance);
        movementResponse.setSubmissionDate(new Date());
        Client client = new Client();
        client.setStatus(true);
        accountResponse = new Account();
        accountResponse.setId(1L);
        accountResponse.setInitialBalance(2000);
        accountResponse.setNumber(1L);
        accountResponse.setClient(client);
        accountResponse.setStatus(true);
    }

    @Test
    @Order(1)
    void givenValidMovementInfoAndItsFirstMovementAccountWhenSaveMovementThenReturnMovementPersistedInfo() {
        when(accountRepository.findByNumber(accountResponse.getNumber())).thenReturn(Optional.of(accountResponse));
        when(mapper.map(any(), eq(Account.class))).thenReturn(new Account());
        when(repository.findFirstByAccountNumberOrderBySubmissionDateAsc(movementRequest.getAccountNumber()))
                .thenReturn(Optional.empty());
        when(mapper.map(any(), eq(MovementResponse.class))).thenReturn(movementResponse);
        MovementResponse movementRes = movementService.saveMovement(movementRequest);
        assertNotNull(movementRes);
    }

    @Test
    @Order(2)
    void givenValidMovementInfoAndItsNotFirstMovementAccountWhenSaveMovementThenReturnMovementPersistedInfo() {
        when(accountRepository.findByNumber(accountResponse.getNumber())).thenReturn(Optional.of(accountResponse));
        when(mapper.map(any(), eq(Account.class))).thenReturn(new Account());
        when(repository.findFirstByAccountNumberOrderBySubmissionDateAsc(movementRequest.getAccountNumber()))
                .thenReturn(Optional.of(movement));
        when(mapper.map(any(), eq(MovementResponse.class))).thenReturn(movementResponse);
        MovementResponse movementRes = movementService.saveMovement(movementRequest);
        assertNotNull(movementRes);
    }

    @Test
    @Order(3)
    void givenValidMovementInfoAndItsInsufficientAmountWhenSaveMovementThenThrowsBadRequestException() {
        movement.setBalance(500);
        movementRequest.setAmount(-1000d);
        when(accountRepository.findByNumber(accountResponse.getNumber())).thenReturn(Optional.of(accountResponse));
        when(repository.findFirstByAccountNumberOrderBySubmissionDateAsc(movementRequest.getAccountNumber()))
                .thenReturn(Optional.of(movement));
        assertThrows(BadRequestException.class, () -> movementService.saveMovement(movementRequest));
    }

    @Test
    @Order(4)
    void givenValidMovementInfoAndClientIsDisabledWhenSaveMovementThenThrowsBadRequestException() {
        movement.setBalance(500);
        movementRequest.setAmount(-1000d);
        accountResponse.getClient().setStatus(false);
        when(accountRepository.findByNumber(accountResponse.getNumber())).thenReturn(Optional.of(accountResponse));
        assertThrows(BadRequestException.class, () -> movementService.saveMovement(movementRequest));
    }
}