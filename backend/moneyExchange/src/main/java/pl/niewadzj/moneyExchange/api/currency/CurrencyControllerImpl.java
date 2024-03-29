package pl.niewadzj.moneyExchange.api.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.currency.interfaces.CurrencyController;
import pl.niewadzj.moneyExchange.api.currency.interfaces.CurrencyService;
import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;

import java.util.List;

import static pl.niewadzj.moneyExchange.api.currency.constants.CurrencyMappings.CURRENCY_MAPPING;
import static pl.niewadzj.moneyExchange.api.currency.constants.CurrencyMappings.GET_ALL_MAPPING;
import static pl.niewadzj.moneyExchange.api.currency.constants.CurrencyMappings.GET_ONE_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(CURRENCY_MAPPING)
public class CurrencyControllerImpl implements CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(GET_ALL_MAPPING)
    public final List<CurrencyResponse> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @GetMapping(GET_ONE_MAPPING)
    public final CurrencyResponse getCurrency(@RequestParam Long id) {
        return currencyService.getCurrency(id);
    }


}
