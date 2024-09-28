package pl.niewadzj.moneyExchange.entities.message.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;

@Repository
public interface ValueMessageRepository extends JpaRepository<ValueMessage, Long> {

}
