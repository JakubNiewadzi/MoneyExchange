package pl.niewadzj.moneyExchange.api.currencyExchange;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeController;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyHistoryResponse;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeMappings.CURRENCY_EXCHANGE_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeMappings.EXCHANGE_CURRENCY_MAPPING;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeMappings.GET_EXCHANGES_FOR_USER;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeMappings.REVERT_EXCHANGE;

@RestController
@RequiredArgsConstructor
@RequestMapping(CURRENCY_EXCHANGE_MAPPING)
public class CurrencyExchangeControllerImpl implements CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @Override
    @PostMapping(EXCHANGE_CURRENCY_MAPPING)
    public final ExchangeCurrencyResponse exchangeCurrency(@RequestBody @Valid ExchangeCurrencyRequest exchangeCurrencyRequest,
                                                           @AuthenticationPrincipal User user) {
        return currencyExchangeService.exchangeCurrency(exchangeCurrencyRequest, user);
    }

    @Override
    @GetMapping(GET_EXCHANGES_FOR_USER)
    public final CurrencyHistoryResponse getExchangesHistoryForUser(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                                    @AuthenticationPrincipal User user) {
        return currencyExchangeService.getExchangesHistoryForUser(pageNo, pageSize, user);
    }

    @Override
    @PostMapping(REVERT_EXCHANGE)
    public final ExchangeCurrencyResponse revertExchange(@RequestParam Long id,
                                                         @AuthenticationPrincipal User user) {
        return currencyExchangeService.revertExchange(id, user);
    }


}
