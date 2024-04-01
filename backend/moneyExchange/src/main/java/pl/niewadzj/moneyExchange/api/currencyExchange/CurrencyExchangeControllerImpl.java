package pl.niewadzj.moneyExchange.api.currencyExchange;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeController;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeRequest;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.TransactionMappings.MAKE_TRANSACTION_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.TransactionMappings.TRANSACTION_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(TRANSACTION_MAPPING)
public class CurrencyExchangeControllerImpl implements CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @Override
    @PostMapping(MAKE_TRANSACTION_MAPPING)
    public final CurrencyExchangeResponse exchangeCurrency(@RequestBody @Valid CurrencyExchangeRequest currencyExchangeRequest,
                                                           @AuthenticationPrincipal User user) {
        return currencyExchangeService.exchangeCurrency(currencyExchangeRequest, user);
    }

}
