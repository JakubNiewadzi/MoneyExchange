package pl.niewadzj.moneyExchange.entities.message.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;

@Repository
public interface DateMessageRepository extends JpaRepository<DateMessage, Long> {

}
