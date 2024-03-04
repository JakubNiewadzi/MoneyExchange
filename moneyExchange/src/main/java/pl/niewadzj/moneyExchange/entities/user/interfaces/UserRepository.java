package pl.niewadzj.moneyExchange.entities.user.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}