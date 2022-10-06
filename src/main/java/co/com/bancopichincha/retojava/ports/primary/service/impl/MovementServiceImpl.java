package co.com.bancopichincha.retojava.ports.primary.service.impl;

import co.com.bancopichincha.retojava.adapters.primary.rest.request.MovementRequest;
import co.com.bancopichincha.retojava.adapters.primary.rest.response.MovementResponse;
import co.com.bancopichincha.retojava.domain.Account;
import co.com.bancopichincha.retojava.domain.Movement;
import co.com.bancopichincha.retojava.domain.repository.AccountRepository;
import co.com.bancopichincha.retojava.domain.repository.MovementRepository;
import co.com.bancopichincha.retojava.exception.BadRequestException;
import co.com.bancopichincha.retojava.exception.NotFoundException;
import co.com.bancopichincha.retojava.ports.primary.service.MovementService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository repository;

    private final AccountRepository accountRepository;

    private final ModelMapper mapper;


    @Override
    public MovementResponse saveMovement(MovementRequest movement) {
        Account account = getAccount(movement.getAccountNumber());
        if (account.isStatus() && account.getClient().isStatus()) {
            Movement lastMovement = repository
                    .findFirstByAccountNumberOrderBySubmissionDateAsc(movement.getAccountNumber())
                    .orElse(null);
            double balance = lastMovement != null ? lastMovement.getBalance() + movement.getAmount()
                    : account.getInitialBalance() + movement.getAmount();
            if (balance >= 0) {
                Movement mov = new Movement();
                mov.setAccount(mapper.map(account, Account.class));
                mov.setAmount(movement.getAmount());
                mov.setBalance(balance);
                mov.setSubmissionDate(new Date());
                return mapper.map(repository.save(mov), MovementResponse.class);
            } else {
                throw new BadRequestException("Insufficient amount");
            }
        } else {
            throw new BadRequestException("Client or account disabled");
        }
    }

    private Account getAccount(long number) {
        return accountRepository.findByNumber(number).orElseThrow(
                () -> new NotFoundException(String.format("Account not found with this number %d",
                        number)));
    }
}
