package co.com.bancopichincha.retojava.ports.primary.service;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.AccountRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse saveAccount(AccountRequest account);

    void updateAccount(long id, AccountRequest account);

    List<AccountResponse> getAccounts(Long number, String type, Boolean status, Long clientId);

    AccountResponse getAccountByNumber(long number);

    AccountResponse getAccount(long id);

    void deleteAccount(long id);

}
