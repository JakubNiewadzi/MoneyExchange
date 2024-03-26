package pl.niewadzj.moneyExchange.api.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.transaction.interfaces.TransactionController;
import pl.niewadzj.moneyExchange.api.transaction.interfaces.TransactionService;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionRequest;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.transaction.constants.TransactionMappings.MAKE_TRANSACTION_MAPPING;
import static pl.niewadzj.moneyExchange.api.transaction.constants.TransactionMappings.TRANSACTION_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSACTION_MAPPING)
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    @Override
    @PostMapping(MAKE_TRANSACTION_MAPPING)
    public final TransactionResponse makeTransaction(@RequestBody @Valid TransactionRequest transactionRequest,
                                                     @AuthenticationPrincipal User user) {
        return transactionService.makeTransaction(transactionRequest, user);
    }

}
