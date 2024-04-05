package pl.niewadzj.moneyExchange.entities.transfer.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.transfer.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Page<Transfer> findByProviderAccountOrReceiverAccount(Account providerAccount,
                                                          Account receiverAccount,
                                                          Pageable pageable);

    Page<Transfer> findByProviderAccount(Account providerAccount,
                                         Pageable pageable);

    Page<Transfer> findByReceiverAccount(Account receiverAccount,
                                         Pageable pageable);

}
