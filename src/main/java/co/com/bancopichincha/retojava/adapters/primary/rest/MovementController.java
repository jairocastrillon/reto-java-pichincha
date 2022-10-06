package co.com.bancopichincha.retojava.adapters.primary.rest;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.MovementRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.MovementResponse;
import co.com.bancopichincha.retojava.ports.primary.service.MovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/movimientos")
@AllArgsConstructor
public class MovementController {

    private final MovementService service;

    @PostMapping
    public ResponseEntity<MovementResponse> saveMovement(@RequestBody @Valid MovementRequest movement) {
        return new ResponseEntity<>(service.saveMovement(movement), HttpStatus.CREATED);
    }

}
