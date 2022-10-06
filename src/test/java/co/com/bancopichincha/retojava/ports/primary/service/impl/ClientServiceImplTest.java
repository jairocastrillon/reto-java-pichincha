package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.ClientRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ReporteResponse;
import co.com.bancopichincha.retojava.domain.Client;
import co.com.bancopichincha.retojava.domain.Movement;
import co.com.bancopichincha.retojava.domain.repository.ClientRepository;
import co.com.bancopichincha.retojava.domain.repository.MovementRepository;
import co.com.bancopichincha.retojava.exception.BadRequestException;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private MovementRepository movementRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    private static ClientResponse clientResponse;

    private static ClientRequest clientRequest;

    private static Client client;

    private static final long ID = 1L;

    private static Movement movement;

    private static ReporteResponse reporteResponse;

    @BeforeAll
    public static void setUp() {
        String name = "user proof";
        String genre = "MASCULINO";
        int age = 20;
        String address = "Calle luna # 5-40";
        String phone = "54837483";
        String clientId = "user.proof";
        String password = "1234";
        boolean status = true;
        client = new Client(ID, name, genre, age, address, phone, clientId, password, status);
        clientRequest = new ClientRequest();
        clientRequest.setId(ID);
        clientRequest.setName(name);
        clientRequest.setGenre(genre);
        clientRequest.setAge(age);
        clientRequest.setAddress(address);
        clientRequest.setPhone(phone);
        clientRequest.setClientId(clientId);
        clientRequest.setPassword(password);
        clientRequest.setStatus(status);
        clientResponse = new ClientResponse();
        clientResponse.setId(ID);
        clientResponse.setName(name);
        clientResponse.setGenre(genre);
        clientResponse.setAge(age);
        clientResponse.setAddress(address);
        clientResponse.setPhone(phone);
        clientResponse.setClientId(clientId);
        clientResponse.setPassword(password);
        clientResponse.setStatus(status);
        movement = new Movement();
        reporteResponse = new ReporteResponse();
    }

    @Test
    void givenValidClientInfoWhenSaveClientThenReturnClientPersistedInfo() {
        when(mapper.map(any(), eq(Client.class))).thenReturn(client);
        when(mapper.map(any(), eq(ClientResponse.class))).thenReturn(clientResponse);
        when(repository.save(client)).thenReturn(client);
        ClientResponse clientRes = clientService.saveClient(clientRequest);
        assertNotNull(clientRes);
    }

    @Test
    void givenValidClientInfoWhenUpdateClientThenDoesntThrow() {
        when(repository.findById(client.getId())).thenReturn(Optional.of(client));
        when(mapper.map(any(), eq(ClientResponse.class))).thenReturn(clientResponse);
        when(mapper.map(any(), eq(Client.class))).thenReturn(client);
        when(repository.save(client)).thenReturn(client);
        assertDoesNotThrow(() -> clientService.updateClient(clientRequest.getId(), clientRequest));
    }

    @Test
    void givenNotValidClientIdentifierWhenUpdateClientThenThrowNotFoundException() {
        when(repository.findById(client.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.updateClient(ID, clientRequest));
    }

    @Test
    void whenGetClientsThenReturnClientsInfo() {
        when(repository.filter(null, null)).thenReturn(List.of(client));
        when(mapper.map(any(), eq(ClientResponse.class))).thenReturn(clientResponse);
        List<ClientResponse> clients = clientService.filter(null, null);
        assertAll(() -> assertNotNull(clients), () -> assertEquals(1, clients.size()));
    }

    @Test
    void givenValidClientIdentifierWhenGetClientThenReturnClientInfo() {
        when(repository.findById(client.getId())).thenReturn(Optional.of(client));
        when(mapper.map(any(), eq(ClientResponse.class))).thenReturn(clientResponse);
        ClientResponse clientRes = clientService.getClient(client.getId());
        assertNotNull(clientRes);
    }

    @Test
    void givenNotValidClientIdentifierWhenGetClientThenThrowsNotFoundException() {
        when(repository.findById(client.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.getClient(ID));
    }

    @Test
    void givenClientIdentifierAndDatesWhenGenerateReportThenReturnReporte() {
        when(movementRepository.findByAccountClientIdAndSubmissionDateBetween(anyLong(), any(), any()))
                .thenReturn(List.of(movement));
        when(mapper.map(any(), eq(ReporteResponse.class))).thenReturn(reporteResponse);
        List<ReporteResponse> reporte = clientService.generateReport(client.getId(),
                "01/01/2022", "12/12/2022");
        assertAll(() -> assertNotNull(reporte), () -> assertEquals(1, reporte.size()));
    }

    @Test
    void givenInvalidDateFormatsWhenGenerateReportThenThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> clientService.generateReport(ID,
                "ok", "12/12/2022"));
    }

    @Test
    void givenStartDateAfterThanEndDateWhenGenerateReportThenThrowsBadRequestException() {
        assertThrows(BadRequestException.class, () -> clientService.generateReport(ID,
                "12/12/2022", "01/01/2022"));
    }

    @Test
    void givenValidClientIdentifierWhenDeleteClientThenDoesntThrow() {
        when(repository.findById(client.getId())).thenReturn(Optional.of(client));
        when(mapper.map(any(), eq(ClientResponse.class))).thenReturn(clientResponse);
        assertDoesNotThrow(() -> clientService.deleteClient(client.getId()));
    }

    @Test
    void givenNotValidClientIdentifierWhenDeleteClientThenThrowsNotFoundException() {
        long id = client.getId();
        when(repository.findById(client.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> clientService.deleteClient(id));
    }

}