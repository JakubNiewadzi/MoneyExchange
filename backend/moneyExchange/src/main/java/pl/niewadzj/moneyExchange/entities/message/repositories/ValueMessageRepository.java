package pl.niewadzj.moneyExchange.entities.message.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;

@Repository
public interface ValueMessageRepository extends CrudRepository<ValueMessage, Long> {
}
