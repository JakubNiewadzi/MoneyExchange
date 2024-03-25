package pl.niewadzj.moneyExchange.api.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountController;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.api.account.records.TransferRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.ACCOUNT_MAPPING;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.DEPOSIT_MAPPING;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_ACTIVE_ACCOUNTS;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_CURRENCY_ACCOUNT;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.GET_CURRENCY_ACCOUNTS;
import static pl.niewadzj.moneyExchange.api.account.constants.AccountMappings.WITHDRAW_MAPPING;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ACCOUNT_MAPPING)
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    @Override
    @PutMapping(DEPOSIT_MAPPING)
    public BalanceResponse depositToAccount(@RequestBody @Valid TransferRequest transferRequest,
                                            @AuthenticationPrincipal User user) {
        return accountService.depositToAccount(transferRequest, user);
    }

    @Override
    @PutMapping(WITHDRAW_MAPPING)
    public BalanceResponse withdrawFromAccount(@RequestBody @Valid TransferRequest transferRequest,
                                               @AuthenticationPrincipal User user) {
        return accountService.withdrawFromAccount(transferRequest, user);
    }

    @Override
    @GetMapping(GET_CURRENCY_ACCOUNTS)
    public List<CurrencyAccountResponse> getCurrencyAccounts(@AuthenticationPrincipal User user) {
        return accountService.getCurrencyAccounts(user);
    }

    @Override
    @GetMapping(GET_CURRENCY_ACCOUNT)
    public CurrencyAccountResponse getCurrencyAccountByCurrencyId(@RequestParam Long currencyId,
                                                                  @AuthenticationPrincipal User user) {
        return accountService.getCurrencyAccountByCurrencyId(currencyId, user);
    }

    @Override
    @GetMapping(GET_ACTIVE_ACCOUNTS)
    public List<CurrencyAccountResponse> getActiveCurrencyAccounts(@AuthenticationPrincipal User user) {
        return accountService.getActiveCurrencyAccounts(user);
    }

}
