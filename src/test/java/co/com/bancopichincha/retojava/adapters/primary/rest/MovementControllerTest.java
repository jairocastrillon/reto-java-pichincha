package co.com.bancopichincha.retojava.adapters.primary.rest;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.MovementRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.MovementResponse;
import co.com.bancopichincha.retojava.ports.primary.service.MovementService;
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

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovementControllerTest {

    private static final String BASE_URL = "http://localhost:8080/movimientos";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovementService service;

    private static ObjectMapper mapper;

    private static MovementResponse movementResponse;

    private static MovementRequest movementRequest;

    @BeforeAll
    public static void setUp() {
        double amount = 200;
        double balance = 300;
        mapper = new ObjectMapper();
        movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(1L);
        movementRequest.setAmount(amount);
        movementResponse = new MovementResponse();
        movementResponse.setAccountNumber(1L);
        movementResponse.setAmount(amount);
        movementResponse.setBalance(balance);
        movementResponse.setSubmissionDate(new Date());
    }

    @Test
    @Order(1)
    void givenValidMovementWhenSaveMovementThenReturnCreatedStatus() throws Exception {
        when(service.saveMovement(movementRequest)).thenReturn(movementResponse);
        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(movementRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void givenNotValidMovementWhenSaveMovementThenReturnBadRequestStatus() throws Exception {
        movementRequest.setAmount(null);
        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(movementRequest)))
                .andExpect(status().isBadRequest());
    }
}