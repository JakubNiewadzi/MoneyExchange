package pl.niewadzj.moneyExchange.api.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACCOUNT;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACCOUNT_BY_NUMBER;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACTIVE_ACCOUNTS;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_CURRENCY_ACCOUNTS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ACCOUNT_MAPPING)
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @GetMapping(GET_CURRENCY_ACCOUNTS)
    public final List<CurrencyAccountResponse> getCurrencyAccounts(@AuthenticationPrincipal User user) {
        return accountService.getCurrencyAccounts(user);
    }

    @Override
    @GetMapping(GET_ACTIVE_ACCOUNTS)
    public final List<CurrencyAccountResponse> getActiveCurrencyAccounts(@AuthenticationPrincipal User user) {
        return accountService.getActiveCurrencyAccounts(user);
    }

    @Override
    @GetMapping(GET_ACCOUNT_BY_NUMBER)
    public final AccountResponse getAccountByAccountNumber(@RequestParam String accountNumber, @AuthenticationPrincipal User user) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    @Override
    @GetMapping(GET_ACCOUNT)
    public final AccountResponse getAccountForUser(@AuthenticationPrincipal User user) {
        return accountService.getAccountForUser(user);
    }

}
