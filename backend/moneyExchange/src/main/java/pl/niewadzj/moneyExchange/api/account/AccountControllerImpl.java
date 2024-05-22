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
import pl.niewadzj.moneyExchange.api.account.records.AccountUserInfoResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountsPageResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.api.account.constants.AccountConstants.PAGE_NUMBER;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountConstants.PAGE_SIZE;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACCOUNT;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACCOUNTS;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACCOUNT_BY_NUMBER;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACTIVE_ACCOUNTS;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ALL_CURRENCY_ACCOUNTS_FOR_USER;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_CURRENCY_ACCOUNTS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ACCOUNT_MAPPING)
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @GetMapping(GET_CURRENCY_ACCOUNTS)
    public final CurrencyAccountsPageResponse getCurrencyAccounts(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                                  @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize, @AuthenticationPrincipal User user) {
        return accountService.getCurrencyAccounts(pageNo, pageSize, user);
    }

    @Override
    @GetMapping(GET_ACTIVE_ACCOUNTS)
    public final CurrencyAccountsPageResponse getActiveCurrencyAccounts(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                                        @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize, @AuthenticationPrincipal User user) {
        return accountService.getActiveCurrencyAccounts(pageNo, pageSize, user);
    }

    @Override
    @GetMapping(GET_ALL_CURRENCY_ACCOUNTS_FOR_USER)
    public List<CurrencyAccountResponse> getAllCurrencyAccountsForUser(@AuthenticationPrincipal User user) {
        return accountService.getAllCurrencyAccountsForUser(user);
    }

    @Override
    @GetMapping(GET_ACCOUNT_BY_NUMBER)
    public final AccountResponse getAccountByAccountNumber(@RequestParam String accountNumber, @AuthenticationPrincipal User user) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }

    @Override
    @GetMapping(GET_ACCOUNT)
    public final AccountUserInfoResponse getAccountForUser(@AuthenticationPrincipal User user) {
        return accountService.getAccountForUser(user);
    }

    @Override
    @GetMapping(GET_ACCOUNTS)
    public final List<AccountResponse> getAllAccounts(@RequestParam(defaultValue = PAGE_NUMBER, required = false) int pageNo,
                                                      @RequestParam(defaultValue = PAGE_SIZE, required = false) int pageSize) {
        return accountService.getAccounts(pageNo, pageSize);
    }


}
