package pl.niewadzj.moneyExchange.entities.message.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;

import java.util.List;

@Repository
public interface DateMessageRepository extends JpaRepository<DateMessage, Long> {

    List<DateMessage> findByUserId(Long id);

}
