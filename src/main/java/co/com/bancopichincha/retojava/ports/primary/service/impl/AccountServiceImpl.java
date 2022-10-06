package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.AccountRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;
import co.com.bancopichincha.retojava.domain.Account;
import co.com.bancopichincha.retojava.domain.enums.AccountType;
import co.com.bancopichincha.retojava.domain.repository.AccountRepository;
import co.com.bancopichincha.retojava.exception.BadRequestException;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.AccountService;
import co.com.bancopichincha.retojava.ports.primary.service.ClientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private final ClientService clientService;

    private final ModelMapper mapper;

    @Override
    public AccountResponse saveAccount(AccountRequest account) {
        if (clientService.getClient(account.getClientId()).isStatus()) {
            AccountType.findAccount(account.getType());
            return mapper.map(repository.save(mapper.map(account, Account.class)), AccountResponse.class);
        } else {
            throw new BadRequestException("Client is not active");
        }
    }

    @Override
    public List<AccountResponse> getAccounts(Long number, String type, Boolean status, Long clientId) {
        if (type != null) {
            AccountType.findAccount(type);
        }
        return repository.getAccounts(number, type, status, clientId).stream()
                .map(a -> mapper.map(a, AccountResponse.class)).collect(Collectors.toList());
    }

    @Override
    public AccountResponse getAccountByNumber(long number) {
        return repository.findByNumber(number).map(a -> mapper.map(a, AccountResponse.class))
                .orElseThrow(() -> new NotFoundException(String.format("Account not found with" +
                                " number %d", number)));
    }

    @Override
    public AccountResponse getAccount(long id) {
        return repository.findById(id).map(a -> mapper.map(a, AccountResponse.class)).orElseThrow(
                () -> new NotFoundException(String.format("Account not found with identifier %d",
                        id)));
    }

    @Override
    public void deleteAccount(long id) {
        getAccount(id);
        repository.deleteById(id);
    }
}
