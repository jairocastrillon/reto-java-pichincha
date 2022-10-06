package co.com.bancopichincha.retojava.adapters.primary.rest;


import co.com.bancopichincha.retojava.adapters.primary.rest.request.ClientRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ReporteResponse;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.ClientService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientControllerTest {

    private static final String BASE_URL = "http://localhost:8080/clientes";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    private static ObjectMapper mapper;

    private static ClientResponse clientResponse;

    private static ClientRequest clientRequest;

    private static ReporteResponse reporteResponse;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        long id = 1L;
        String name = "user proof";
        String genre = "MASCULINO";
        int age = 20;
        String address = "Calle luna # 5-40";
        String phone = "54837483";
        String clientId = "user.proof";
        String password = "1234";
        boolean status = true;
        clientRequest = new ClientRequest();
        clientRequest.setId(id);
        clientRequest.setName(name);
        clientRequest.setGenre(genre);
        clientRequest.setAge(age);
        clientRequest.setAddress(address);
        clientRequest.setPhone(phone);
        clientRequest.setClientId(clientId);
        clientRequest.setPassword(password);
        clientRequest.setStatus(status);
        clientResponse = new ClientResponse();
        clientResponse.setId(id);
        clientResponse.setName(name);
        clientResponse.setGenre(genre);
        clientResponse.setAge(age);
        clientResponse.setAddress(address);
        clientResponse.setPhone(phone);
        clientResponse.setClientId(clientId);
        clientResponse.setPassword(password);
        clientResponse.setStatus(status);
        reporteResponse = new ReporteResponse();
        reporteResponse.setAmount(200);
        reporteResponse.setBalance(200);
    }

    @Test
    @Order(1)
    void givenValidClientInfoWhenSaveClientThenReturnCreatedStatus() throws Exception {
        when(service.saveClient(any())).thenReturn(clientResponse);
        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mapper
                .writeValueAsString(clientRequest))).andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(clientResponse)));
    }

    @Test
    @Order(2)
    void givenValidClientInfoWhenUpdateClientThenReturnNoContentStatus() throws Exception {
        this.mockMvc.perform(put(BASE_URL + "/{id}", clientResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(clientRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(3)
    void givenValidClientInfoButNotValidIdentifierWhenUpdateClientThenReturnNotFoundStatus() throws Exception {
        doThrow(NotFoundException.class).when(service).updateClient(anyLong(), any());
        this.mockMvc.perform(put(BASE_URL + "/{id}", clientResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(clientRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    void givenNotValidClientInfoWhenSaveClientThenReturnBadRequestStatus() throws Exception {
        clientRequest.setClientId(null);
        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mapper
                .writeValueAsString(clientRequest))).andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void givenNotParameterValuesWhenFilterThenReturnOkStatus() throws Exception {
        when(service.filter(any(), any())).thenReturn(List.of(clientResponse));
        this.mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(mapper
                        .writeValueAsString(List.of(clientResponse))));
    }

    @Test
    @Order(6)
    void givenValidClientIdentifierWhenGetClientThenReturnOkStatus() throws Exception {
        when(service.getClient(anyLong())).thenReturn(clientResponse);
        this.mockMvc.perform(get(BASE_URL + "/{id}", clientResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(clientResponse)));
    }

    @Test
    @Order(7)
    void givenNotValidClientIdentifierWhenGetClientThenReturnNotFoundStatus() throws Exception {
        when(service.getClient(anyLong())).thenThrow(NotFoundException.class);
        this.mockMvc.perform(get(BASE_URL + "/{id}", clientResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void givenValidDataWhenGenerateReportThenReturnOkStatus() throws Exception {
        when(service.generateReport(anyLong(), anyString(), anyString())).thenReturn(List
                .of(reporteResponse));
        this.mockMvc.perform(get(BASE_URL +  "/reportes")
                .contentType(MediaType.APPLICATION_JSON).param("id", String
                        .valueOf(clientResponse.getId()))
                .param("date_start", "01/01/2022")
                .param("date_end", "05/10/2022")).andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(List.of(reporteResponse))));
    }

    @Test
    @Order(9)
    void givenValidClientIdentifierWhenDeleteClientThenReturnNoContentStatus() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + "/{id}", clientResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void givenNotValidClientIdentifierWhenDeleteClientThenReturnNotFoundStatus() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteClient(anyLong());
        this.mockMvc.perform(delete(BASE_URL + "/{id}", clientResponse.getId())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

}