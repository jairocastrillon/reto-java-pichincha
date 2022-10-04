package co.com.bancopichincha.retojava.ports.primary.service;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.ClientRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse saveClient(ClientRequest client);

    void updateClient(long id, ClientRequest client);

    List<ClientResponse> filter(String name, String genre);

    ClientResponse getClient(long id);

    void deleteClient(long id);

}
