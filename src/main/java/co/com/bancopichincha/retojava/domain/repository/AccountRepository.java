package co.com.bancopichincha.retojava.domain.repository;

import co.com.bancopichincha.retojava.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE (?1 IS NULL OR a.number = ?1)" +
            " AND (?2 IS NULL OR a.type = ?2) AND (?3 IS NULL OR a.status = ?3)" +
            " AND (?3 IS NULL OR a.client.id = ?3)")
    List<Account> getAccounts(Long number, String type, Boolean status, Long clientId);

    Optional<Account> findByNumber(long id);

}
