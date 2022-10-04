package co.com.bancopichincha.retojava.domain.repository;

import co.com.bancopichincha.retojava.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE (?1 IS NULL OR UPPER(c.name) = UPPER(?1))" +
            " AND (?2 IS NULL OR c.genre = ?2)")
    List<Client> filter(String name, String genre);
}
