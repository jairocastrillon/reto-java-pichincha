package co.com.bancopichincha.retojava.adapters.primary.rest;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.AccountRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;
import co.com.bancopichincha.retojava.ports.primary.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
@AllArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponse> saveAccount(@RequestBody @Valid AccountRequest account) {
        return new ResponseEntity<>(service.saveAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(
            @RequestParam(value = "number", required = false) Long number,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "status", required = false) Boolean status,
            @RequestParam(value = "client_id", required = false) Long clientId) {
        return new ResponseEntity<>(service.getAccounts(number, type, status, clientId),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable long id) {
        return new ResponseEntity<>(service.getAccount(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

}
