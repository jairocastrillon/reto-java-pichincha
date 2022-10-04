package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.ClientRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;
import co.com.bancopichincha.retojava.domain.Client;
import co.com.bancopichincha.retojava.domain.repository.ClientRepository;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    private final ModelMapper mapper;

    @Override
    public ClientResponse saveClient(ClientRequest client) {
        return mapper.map(repository.save(mapper.map(client, Client.class)), ClientResponse.class);
    }

    @Override
    public void updateClient(long id, ClientRequest client) {
        getClient(id);
        Client clientEntity = mapper.map(client, Client.class);
        clientEntity.setId(id);
        repository.save(clientEntity);
    }

    @Override
    public List<ClientResponse> filter(String name, String genre) {
        return repository.filter(name, genre).stream().map(c -> mapper.map(c, ClientResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse getClient(long id) {
        return repository.findById(id).map(c -> mapper.map(c, ClientResponse.class)).orElseThrow(
                () -> new NotFoundException(String.format("Client not found with identifier: %d", id)));
    }

    @Override
    public void deleteClient(long id) {
        getClient(id);
        repository.deleteById(id);
    }

}
