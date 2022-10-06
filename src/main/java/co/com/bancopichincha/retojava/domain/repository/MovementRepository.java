package co.com.bancopichincha.retojava.domain.repository;

import co.com.bancopichincha.retojava.domain.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    Optional<Movement> findFirstByAccountNumberOrderBySubmissionDateAsc(long number);

    List<Movement> findByAccountClientIdAndSubmissionDateBetween(long id, Date dateStart,
                                                                 Date dateEnd);

}
