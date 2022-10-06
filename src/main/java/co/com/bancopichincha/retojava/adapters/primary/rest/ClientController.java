package co.com.bancopichincha.retojava.adapters.primary.rest;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.ClientRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ReporteResponse;
import co.com.bancopichincha.retojava.ports.primary.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponse> saveClient(@RequestBody @Valid ClientRequest client) {
        return new ResponseEntity<>(service.saveClient(client), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable long id,
                                             @RequestBody @Valid ClientRequest client) {
        service.updateClient(id, client);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> filter(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "genre", required = false) String genre) {
        return new ResponseEntity<>(service.filter(name, genre), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable long id) {
        return new ResponseEntity<>(service.getClient(id), HttpStatus.OK);
    }

    @GetMapping("/reportes")
    public ResponseEntity<List<ReporteResponse>> generateReport(@RequestParam("id") long id,
                                                                @RequestParam("date_start") String dateStart,
                                                                @RequestParam("date_end") String dateEnd) {
        return new ResponseEntity<>(service.generateReport(id, dateStart, dateEnd), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
