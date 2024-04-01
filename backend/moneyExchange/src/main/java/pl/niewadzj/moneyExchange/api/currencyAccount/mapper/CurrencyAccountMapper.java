package pl.niewadzj.moneyExchange.api.currencyAccount.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;

import java.util.function.Function;

@Service
public class CurrencyAccountMapper implements Function<CurrencyAccount, CurrencyAccountResponse> {
    @Override
    public CurrencyAccountResponse apply(CurrencyAccount currencyAccount) {
        return CurrencyAccountResponse.builder()
                .id(currencyAccount.getId())
                .balance(currencyAccount.getBalance())
                .status(currencyAccount.getCurrencyAccountStatus())
                .currencyCode(currencyAccount.getCurrency().getCode())
                .currencyId(currencyAccount.getCurrency().getId())
                .build();
    }
}
